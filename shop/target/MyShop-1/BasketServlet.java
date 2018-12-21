import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;
import java.util.*;
import model.*;
import io.*;
public class BasketServlet extends HttpServlet {
    ArrayList<BasketItem> items = new ArrayList<BasketItem>();
    ArrayList<Item> it = new ArrayList<Item>();
    ArrayList<MyDate> md = new ArrayList<MyDate>();
    protected void service(HttpServletRequest request, HttpServletResponse response)
                      throws ServletException, IOException {
	
	String uri = request.getRequestURI();
	String name = " ";
        HttpSession session = request.getSession(false);
        if(session != null){ 
	     name = (String)session.getAttribute("name");
        }
	StringBuilder sb = new StringBuilder();
	PrintWriter out = response.getWriter();
	out.println("<html><head>");
	out.println("</head><body>");
	items = new ArrayList<BasketItem>();
	it = new ArrayList<Item>();
	new XmlDate().loadDatesFromFile(md);
	new XmlItem().loadItemsFromFile("basket.xml", items);
	new XmlIO().loadItemsFromFile("shop.xml", it);
	name = (String)session.getAttribute("name");
	for(int i = 0; i < items.size(); i++){
		if( uri.equals("/shop/BasketServlet/" + name)) {
			int j = 0;
			
			while (j < items.size()){	
				md.clear();
				new XmlDate().loadDatesFromFile(md);
				if(name.equals(items.get(j).getUsername())){
					String ss = items.get(j).getArt();
					Calendar c = new GregorianCalendar();     
					int p = 0;
					try{
						p += Integer.parseInt(items.get(j).getPrice());
					}catch(Exception e){}
					if( p != 0){
					MyDate d = new MyDate(c.get(Calendar.DAY_OF_MONTH),c.get(Calendar.MONTH),c.get(Calendar.YEAR),p);
							md.add(d);}
					
					for(int k = 0; k < it.size(); k++){
						if(ss.equals(it.get(k).getArt())){
							items.remove(j);
							it.get(k).addItem(-1);     
							new XmlIO().saveItemsToFile("shop.xml", it);
						}
					}
				}
				else j++;
				new XmlDate().saveDatesToFile(md);
			}
			new XmlItem().saveItemsToFile("basket.xml", items);
			
			response.sendRedirect("/shop/BasketServlet");   
			return;   
		}
	}	
	response.setContentType("text/html");
	
	if(session != null){
		if(name.equals("admin")){
			request.getRequestDispatcher("admin.html").include(request, response);            
		} else{
	    	 	request.getRequestDispatcher("auth.html").include(request, response);            
	        }
        }
        else{
            request.getRequestDispatcher("link.html").include(request, response);
        }

	int iq = 0;
	Map<String, Integer> s = new HashMap<String, Integer>();
	Map<String, Integer> k = new HashMap<String, Integer>();
	int t = 0;
	for(int i = 0; i < items.size(); i++){
		
		if(name.equals(items.get(i).getUsername())){
			iq++;
			if(s.containsKey(items.get(i).getName())){
				t = s.get(items.get(i).getName());
				s.put(items.get(i).getName(),t + 1);
			}else{
				s.put(items.get(i).getName(),1);
				try{
					t = Integer.parseInt(items.get(i).getPrice());
				}catch(Exception e){

				}
				k.put(items.get(i).getName(),t);
			}

		}
	}
	
	int id = 0;
	if(iq != 0){
		sb.append("<table>");
		sb.append("<tr><th>Item</th><th>Price</th><th>Amount</th></tr>");
		for (Map.Entry<String, Integer> entry : s.entrySet()) {
			sb.append("<tr>");
			sb.append("<prod>");
			sb.append("<th><product>" + entry.getKey() + "</product></th>");
			sb.append("<th><product>" + k.get(entry.getKey()) + "</product></th>");
			id += k.get(entry.getKey());
			sb.append("<th><product>" + entry.getValue() + "</ptoduct></th>");
			sb.append("</prod>");
			sb.append("</tr>");         
		}
		
		sb.append("</table>");
		sb.append("<form method=\"GET\" action=\"/shop/BasketServlet/" + name + "\">\n");
		sb.append("Summary: " + id);
		sb.append("<input type=\"submit\" value=\"Buy\">\n");
		sb.append("</form>");
	} 
	else{
		sb.append("<check = \"a\" >You have not items in the basket");
	}
	
	
	out.println(sb.toString());
        out.println("</body></html>");
        out.close();
    }
	
	
}
