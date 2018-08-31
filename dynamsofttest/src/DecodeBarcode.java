

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.dynamsoft.barcode.jni.BarcodeReader;
import com.dynamsoft.barcode.jni.TextResult;
import com.google.gson.Gson;


/**
 * Servlet implementation class DecodeBarcode
 */
@WebServlet("/DecodeBarcode")
@MultipartConfig
public class DecodeBarcode extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DecodeBarcode() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	

	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter responseWriter = response.getWriter();
		
		Part imgPart = request.getPart("img");
		
		try {
			// if licenseKeys expire, visit https://www.dynamsoft.com/CustomerPortal/Portal/TrialLicense.aspx to get a try key
			BarcodeReader reader = new BarcodeReader("t0068NQAAAERtbOdlg4/DECVzsG8sTgwp5FvXuHQOVIWZC0tuhM2uNBLedw1Lu88qDeW82b221lw6eklAMj1Y8N5EBq3d9HM=");
			
			// what's template? visit https://www.dynamsoft.com/help/Barcode-Reader/CustomizeTemplate.html
			String exampleTpl = "exampleTpl";
			reader.appendParameterTemplate("{\"ImageParameters\": {\"Name\": \""+exampleTpl+"\",\"BarcodeFormatIds\": [\"All\"],\"ExpectedBarcodesCount\": 512,\"DeblurLevel\": 9,\"AntiDamageLevel\": 9,\"TextFilterMode\": \"Enable\"}}");
			
			// want to use default template? using:
			// reader.decodeFileInMemory(imgPart.getInputStream(), "")
			TextResult[] results = reader.decodeFileInMemory(imgPart.getInputStream(), "exampleTpl");
			
			responseWriter.write(new Gson().toJson(results));
		} catch (Exception e) {
			responseWriter.write(new Gson().toJson(e));
		}finally{
			responseWriter.close();
		}
	}


}
