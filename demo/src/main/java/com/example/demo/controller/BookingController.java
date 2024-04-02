package com.example.demo.controller;

import com.example.demo.entity.ChiTietPhieuDat;
import com.example.demo.entity.HangPhong;
import com.example.demo.entity.LoaiPhong;
import com.example.demo.entity.PhieuDat;
import com.example.demo.repository.HangPhongRepository;
import com.example.demo.repository.LoaiPhongRepository;
import com.example.demo.service.impl.CoinPaymentsService;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class BookingController {
    @Autowired
    private LoaiPhongRepository loaiPhongRepository;
    @Autowired
    private HangPhongRepository hangPhongRepository;

    @GetMapping("booking")
    public String booking(Model model, HttpSession session){

            PhieuDat bookingInfo = (PhieuDat) session.getAttribute("bookingInfo");
            if (bookingInfo==null){
                bookingInfo=new PhieuDat();
                String maPD = UUID.randomUUID().toString().substring(1,7);
                bookingInfo.setMaPD(maPD);
                session.setAttribute("bookingInfo",bookingInfo);

            }
            List<LoaiPhong> loaiPhongs = loaiPhongRepository.findAll();
            model.addAttribute("loaiphongs",loaiPhongs);
            model.addAttribute("checkinDate", bookingInfo.getNgayBD());
            model.addAttribute("checkoutDate", bookingInfo.getNgayTra());
            return "booking";
    }
    @PostMapping("/showHangPhong")
    public String showRoomCategories(@RequestParam("roomType") String roomType,
                                     @RequestParam("checkInDate") String checkInDate,
                                     @RequestParam("checkOutDate") String checkOutDate,
                                     Model model, HttpSession session) {
        // Lấy danh sách hạng phòng cho loại phòng đã chọn
        List<HangPhong> hangPhongs = hangPhongRepository.findByLoaiPhongMaLP(roomType);
        List<LoaiPhong> loaiPhongs = loaiPhongRepository.findAll();
        model.addAttribute("loaiphongs",loaiPhongs);
        session.setAttribute("selectedRoomType", roomType);
        session.setAttribute("checkInDate", checkInDate);
        session.setAttribute("checkOutDate", checkOutDate);
        // Lấy thông tin phiếu đặt từ session
        PhieuDat bookingInfo = (PhieuDat) session.getAttribute("bookingInfo");

        // Tạo một Map để lưu số lượng đã đặt của từng hạng phòng
        Map<String, Integer> soLuongDaDatMap = new HashMap<>();
        Map<String,Integer> soLuongPhongTrong= new HashMap<>();
        // Kiểm tra nếu phiếu đặt không null và chứa chi tiết phiếu đặt
        if (bookingInfo != null && bookingInfo.getChitietphieudats() != null) {
            // Lặp qua danh sách hạng phòng để tính toán số lượng đã đặt
            for (HangPhong hangPhong : hangPhongs) {
                int soLuongDaDat = 0;
                // Lặp qua danh sách chi tiết phiếu đặt của phiếu đặt để tính toán số lượng đã đặt của hạng phòng hiện tại
                for (ChiTietPhieuDat chiTietPhieuDat : bookingInfo.getChitietphieudats()) {
                    if (chiTietPhieuDat.getHangphong().equals(hangPhong)) {
                        soLuongDaDat += chiTietPhieuDat.getSoLuong();
                    }
                }
                // Lưu số lượng đã đặt vào Map
                soLuongDaDatMap.put(hangPhong.getMaHP(), soLuongDaDat);
            }
        }
        for (HangPhong hangPhong : hangPhongs){
            int number=hangPhongRepository.getAvailableRoom(hangPhong.getMaHP(),checkInDate,checkInDate);
            soLuongPhongTrong.put(hangPhong.getMaHP(),number);
        }
        model.addAttribute("soLuongPhongTrong",soLuongPhongTrong);
        model.addAttribute("hangphongs", hangPhongs);
        model.addAttribute("soLuongDaDatMap", soLuongDaDatMap);

        // Các bước còn lại

        return "booking";
    }

    @PostMapping("/bookRoom")
    public String bookRoom(@RequestParam("hangPhongId") List<String> hangPhongIds,
                           @RequestParam("quantity") List<Integer> quantities,
                           HttpSession session) {
        // Lấy thông tin phiếu đặt từ session
        PhieuDat bookingInfo = (PhieuDat) session.getAttribute("bookingInfo");
        double tongtien=bookingInfo.getTongTien();

        // Lặp qua danh sách các hạng phòng và số lượng đã chọn
        for (int i = 0; i < hangPhongIds.size(); i++) {
            String hangPhongId = hangPhongIds.get(i);
            Integer quantity = quantities.get(i);
            if (quantity == 0) {
                continue;
            }
            // Kiểm tra xem hạng phòng đã tồn tại trong chi tiết phiếu đặt hay chưa
            boolean found = false;
            for (ChiTietPhieuDat chiTietPhieuDat : bookingInfo.getChitietphieudats()) {
                if (chiTietPhieuDat.getHangphong().getMaHP().equals(hangPhongId)) {
                    // Cập nhật số lượng nếu đã tồn tại
                    chiTietPhieuDat.setSoLuong(quantity);
                    found = true;
                    break;
                }
            }
            HangPhong selectedRoom = hangPhongRepository.findByMaHP(hangPhongId);
            // Nếu hạng phòng chưa tồn tại trong danh sách chi tiết phiếu đặt, thêm mới
            if (!found) {
                ChiTietPhieuDat chiTietPhieuDat = new ChiTietPhieuDat();
                chiTietPhieuDat.setHangphong(selectedRoom);
                chiTietPhieuDat.setSoLuong(quantity);

                // Thêm chi tiết phiếu đặt vào phiếu đặt
                bookingInfo.addChiTietPhieuDat(chiTietPhieuDat);
            }
            tongtien=tongtien+quantity*selectedRoom.getDonGia();
        }
        bookingInfo.setTongTien(tongtien);
        // Lưu thông tin phiếu đặt đã cập nhật vào session
        session.setAttribute("bookingInfo", bookingInfo);

        return "redirect:/booking-info";
    }

    @GetMapping("/booking-info")
    public String showBookingInfo(HttpSession session, Model model) {
        // Lấy thông tin phiếu đặt từ session
        PhieuDat bookingInfo = (PhieuDat) session.getAttribute("bookingInfo");

        // Truyền thông tin phiếu đặt vào model để hiển thị trên trang HTML
        model.addAttribute("bookingInfo", bookingInfo);

        return "booking-info"; // Trả về trang HTML để hiển thị thông tin phiếu đặt
    }
    @Autowired
    private CoinPaymentsService coinPaymentsService;

    @PostMapping("/process-payment")
    public String processPayment(HttpSession session, Model model) {
        // Lấy thông tin phiếu đặt từ session
        PhieuDat bookingInfo = (PhieuDat) session.getAttribute("bookingInfo");
        System.out.println("chuyen toi checkoutcoin");
            // Thực hiện thanh toán và nhận kết quả từ CoinPayments API
        try {
            String transactionResult = coinPaymentsService.createFixedPricePayment(1,"LTCT","LTCT","votrungquan2002@gmail.com");
            System.out.println(transactionResult);
            // Xử lý kết quả thanh toán
            JSONObject jsonObject = new JSONObject(transactionResult);
            String checkoutUrl = jsonObject.getString("checkout_url");

            // Truyền checkoutUrl vào trang thanh toán
            return "redirect:" + checkoutUrl;
        }
        catch (Exception e){
            return "payment-error";
        }


    }
}
