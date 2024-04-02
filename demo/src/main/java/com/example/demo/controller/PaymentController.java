//import com.example.demo.entity.PhieuDat;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PostMapping;
//import jakarta.servlet.http.HttpSession;
//import java.io.IOException;
//import com.example.demo.service.impl.CoinPaymentsService;
//import org.json.JSONObject;
//
////@Controller
//public class PaymentController {
//
//    @Autowired
//    private CoinPaymentsService coinPaymentsService;
//
//    @PostMapping("/process-payment")
//    public String processPayment(HttpSession session, Model model) {
//        // Lấy thông tin phiếu đặt từ session
//        PhieuDat bookingInfo = (PhieuDat) session.getAttribute("bookingInfo");
//        System.out.println("chuyen toi checkoutcoin");
//        try {
//            // Thực hiện thanh toán và nhận kết quả từ CoinPayments API
//            String transactionResult = coinPaymentsService.createFixedPricePayment(1, "LTCT", "LTCT", "quanbinskt27@gmail.com");
//
//            // Xử lý kết quả thanh toán
//            JSONObject jsonObject = new JSONObject(transactionResult);
//            String checkoutUrl = jsonObject.getString("checkout_url");
//
//            // Truyền checkoutUrl vào trang thanh toán
//            return "redirect:" + checkoutUrl;
//        } catch (IOException e) {
//            // Xử lý lỗi khi gọi API
//            // ...
//
//            // Chuyển hướng người dùng đến trang thông báo lỗi
//            return "payment-error";
//        }
//    }
//}
