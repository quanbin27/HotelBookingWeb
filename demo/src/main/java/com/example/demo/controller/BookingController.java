package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.impl.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.demo.service.impl.CoinPaymentsService.generateHmac;

@Controller
public class BookingController {
    private final PhieuDatServiceImpl phieuDatService;
    private final HangPhongServiceImpl hangPhongService;
    private final UserDetailsServiceImpl userDetailsService;
    private final LoaiPhongServiceImpl loaiPhongService;
    @Autowired
    public BookingController(PhieuDatServiceImpl phieuDatService, HangPhongServiceImpl hangPhongService, UserDetailsServiceImpl userDetailsService, LoaiPhongServiceImpl loaiPhongService) {
        this.phieuDatService = phieuDatService;
        this.hangPhongService = hangPhongService;
        this.userDetailsService = userDetailsService;
        this.loaiPhongService = loaiPhongService;
    }

    @GetMapping("booking")
    public String booking(Model model, HttpSession session){
             Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
             TaiKhoan taiKhoan = userDetailsService.findUserAccount(authentication.getName());
             PhieuDat bookingInfo = (PhieuDat) session.getAttribute("bookingInfo");
             if (bookingInfo==null){
                bookingInfo=new PhieuDat();
                String maPD = UUID.randomUUID().toString().substring(1,7);
                bookingInfo.setMaPD(maPD);
                bookingInfo.setTrangThai("New");
                bookingInfo.setKhachHang(taiKhoan.getKhachHang());
                session.setAttribute("bookingInfo",bookingInfo);
             }
             List<LoaiPhong> loaiPhongs = loaiPhongService.findAll();
             model.addAttribute("loaiphongs",loaiPhongs);
             model.addAttribute("checkinDate", bookingInfo.getNgayBD());
             model.addAttribute("checkoutDate", bookingInfo.getNgayTra());
             return "booking";
    }
    @PostMapping("cancelbooking")
    public String cancelbooking(HttpSession session){
        session.removeAttribute("bookingInfo");
        session.removeAttribute("checkInDate");
        session.removeAttribute("checkOutDate");
        session.removeAttribute("selectedRoomType");
        return "redirect:/booking";
    }
    @PostMapping("/showHangPhong")
    public String showRoomCategories(@RequestParam("roomType") String roomType,
                                     @RequestParam("checkInDate") String checkInDate,
                                     @RequestParam("checkOutDate") String checkOutDate,
                                     Model model, HttpSession session) {
        // Lấy danh sách hạng phòng cho loại phòng đã chọn
        List<HangPhong> hangPhongs =hangPhongService.findByLoaiPhongMaLP(roomType);
        List<LoaiPhong> loaiPhongs = loaiPhongService.findAll();
        model.addAttribute("loaiphongs",loaiPhongs);
        session.setAttribute("selectedRoomType", roomType);
        session.setAttribute("checkInDate", checkInDate);
        session.setAttribute("checkOutDate", checkOutDate);
        String errorMessage = null;
        // Lấy thông tin phiếu đặt từ session
        PhieuDat bookingInfo = (PhieuDat) session.getAttribute("bookingInfo");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // Chuyển đổi chuỗi thành kiểu Date
            Date checkIn = dateFormat.parse(checkInDate);
            Date checkOut = dateFormat.parse(checkOutDate);
            Date today= new Date();

            if (!checkOut.after(checkIn)) {
                errorMessage = "Ngày check-out không được bé hơn ngày check-in. Vui lòng chọn lại ngày.";
            } else {
                bookingInfo.setNgayBD(checkIn);
                bookingInfo.setNgayTra(checkOut);
                bookingInfo.setNgayDat(today);
            }

        } catch (ParseException e) {
            System.out.println("Định dạng ngày không hợp lệ!");
            e.printStackTrace();
        }
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            return "booking"; // trả về trang hiển thị lỗi
        }
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
                    System.out.println("vaoday 1"+chiTietPhieuDat.getHangphong().getMaHP()+chiTietPhieuDat.getSoLuong());
                    if (chiTietPhieuDat.getHangphong().getMaHP().equals(hangPhong.getMaHP())) {
                        System.out.println("vaoday"+chiTietPhieuDat.getSoLuong()+hangPhong.getMaHP());
                        soLuongDaDat += chiTietPhieuDat.getSoLuong();
                    }
                }
                // Lưu số lượng đã đặt vào Map
                soLuongDaDatMap.put(hangPhong.getMaHP(), soLuongDaDat);
                System.out.println(soLuongDaDatMap.toString());
            }
        }
        for (HangPhong hangPhong : hangPhongs){
            int number=hangPhongService.getAvailableRoom(hangPhong.getMaHP(),checkInDate,checkInDate);
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
        System.out.println(bookingInfo.getMaPD()+bookingInfo.getTrangThai());

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
            HangPhong selectedRoom = hangPhongService.findByMaHP(hangPhongId);
            // Nếu hạng phòng chưa tồn tại trong danh sách chi tiết phiếu đặt, thêm mới
            if (!found) {
                ChiTietPhieuDat chiTietPhieuDat = new ChiTietPhieuDat();
                chiTietPhieuDat.setHangphong(selectedRoom);
                chiTietPhieuDat.setSoLuong(quantity);
                chiTietPhieuDat.setPhieudat(bookingInfo);
                bookingInfo.getChitietphieudats().add(chiTietPhieuDat);
            }
        }
        bookingInfo.setTongTien(bookingInfo.calculateTongTien());
        System.out.println(bookingInfo.getChitietphieudats()+"<-CTPD");
//        phieuDatService.save(bookingInfo);
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
        model.addAttribute("checkInDate",session.getAttribute("checkInDate"));
        model.addAttribute("checkOutDate",session.getAttribute("checkOutDate"));
        return "booking-info"; // Trả về trang HTML để hiển thị thông tin phiếu đặt
    }
    @PostMapping("/booking-info")
    public String showBookingInfo(@RequestParam("maPD") String maPD,Model model){
        Optional<PhieuDat> pd=phieuDatService.findById(maPD);
        PhieuDat bookingInfo = pd.get();
        model.addAttribute("bookingInfo",bookingInfo);
        return "booking-info-success";
    }
    @GetMapping("/booking-info-success")
    public String showBookingInfoSuccess(HttpSession session, Model model) {
        // Lấy thông tin phiếu đặt từ session
        PhieuDat bookingInfo = (PhieuDat) session.getAttribute("bookingInfo");
        Optional<PhieuDat> pd=phieuDatService.findById(bookingInfo.getMaPD());
        session.removeAttribute("bookingInfo");
        session.removeAttribute("checkInDate");
        session.removeAttribute("checkOutDate");
        session.removeAttribute("selectedRoomType");
        PhieuDat phieuDat=pd.get();
        model.addAttribute("bookingInfo", phieuDat);
        return "booking-info-success"; // Trả về trang HTML để hiển thị thông tin phiếu đặt
    }
    @Autowired
    private CoinPaymentsService coinPaymentsService;
//    @PostMapping("/process-payment")
//    @ResponseBody
//    public String processPayment(HttpSession session) {
//        System.out.println("Ma PD:" +maPD);
//        PhieuDat bookingInfo = (PhieuDat) session.getAttribute("bookingInfo");
//        System.out.println("chuyen toi checkout coin");
//        try {
//            String transactionResult = coinPaymentsService.createFixedPricePayment(1,"LTCT","LTCT","votrungquan2002@gmail.com",bookingInfo.getKhachHang().getTen(),bookingInfo.getMaPD());
//            System.out.println(transactionResult);
//
//                System.out.println("dung format");
//                JSONObject jsonObject = new JSONObject(transactionResult);
//                // Lấy đối tượng JSON con "result" từ đối tượng JSON chính
//
//                JSONObject resultObject = jsonObject.getJSONObject("result");
//                // Lấy giá trị của trường "checkout_url" từ đối tượng JSON con "result"
//                String checkoutUrl = resultObject.getString("checkout_url");
//                System.out.println(checkoutUrl + "link url");
//                phieuDatService.save(bookingInfo);
//                return checkoutUrl;
//
//        } catch (Exception e) {
//            System.err.println("Lỗi coinPaymentsService: " + e.getMessage());
//            return "payment-error: Unexpected error";
//        }
//    }
    @PostMapping("/coinpayments-ipn")
    public ResponseEntity<String> handleCoinPaymentsIPN(HttpServletRequest request) {
        System.out.println("Da nhan IPN");
        try {
            // Lấy dữ liệu POST từ CoinPayments
            BufferedReader reader = request.getReader();
            StringBuilder postData = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                postData.append(line);
            }

            // Xác minh chữ ký HMAC
            String ipnSecret = "Bin27052002@"; // Thay thế bằng IPN Secret của bạn
            String hmacHeader = request.getHeader("HMAC");
            if (hmacHeader == null || hmacHeader.isEmpty()) {
                return ResponseEntity.badRequest().body("No HMAC signature sent");
            }

             try {
                 String calculatedHmac = generateHmac(ipnSecret,postData.toString());
                 if (!hmacHeader.equals(calculatedHmac)) {
                     return ResponseEntity.badRequest().body("HMAC signature does not match");
                 }
             } catch (Exception e){
                 return ResponseEntity.badRequest().body("HMAC generate Error");
             }
            System.out.println("Dung HMAC ->Xu ly IPN");
            // Xử lý thông báo IPN
            System.out.println("Postdata: " + postData);
            String[] keyValuePairs = postData.toString().split("&");

// Tạo một Map để lưu trữ dữ liệu
            Map<String, String> dataMap = new HashMap<>();

// Lặp qua từng cặp key-value và thêm vào Map
            for (String pair : keyValuePairs) {
                String[] entry = pair.split("=");
                if (entry.length == 2) {
                    String key = entry[0];
                    String value = entry[1];
                    dataMap.put(key, value);
                }
            }
            String ipnType = dataMap.get("ipn_type");
            System.out.println("da get ipn_type:"+ipnType);
            if ("simple".equals(ipnType)) {
                String status = dataMap.get("status");
                String maPd= dataMap.get("item_name");
                Optional<PhieuDat> pd=phieuDatService.findById(maPd);
                PhieuDat phieuDat=pd.get();
                if (status.equals("0")) {
                    phieuDat.setTrangThai("Waiting for buyer funds");
                    phieuDatService.save(phieuDat);
                }
                else if ( status.equals("100")||status.equals("2")) {
                    phieuDat.setTrangThai("Payment Complete");
                    phieuDatService.save(phieuDat);
                }
                else if ( status.equals("-1")) {
                    phieuDat.setTrangThai("Cancelled / Timed Out");
                    phieuDatService.save(phieuDat);
                }
            }
            return ResponseEntity.ok("IPN processed successfully");
        } catch (IOException | JSONException e) {
            System.out.println("Json+IO"+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing IPN: " + e.getMessage());
        }
    }
}
