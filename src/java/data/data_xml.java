/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

/**
 *
 * @author ?
 */
@WebServlet(name = "data_xml", urlPatterns = {"/data_xml"})
public class data_xml extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * 
     * 
     */
    
    // Create storage arrays
    ArrayList<Integer> unsortedArray; //= new ArrayList<Integer>();
    ArrayList<Integer> sortedArray; // = new ArrayList<Integer>();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/xml;charset=utf-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession(true);
            
            // Get the number of items
            int number_of_items = Integer.parseInt(request.getParameter("number_of_items"));
            
            // init unsorted array
            unsortedArray = new ArrayList<Integer>();
            
            // Get all the items
            for (int iterator = 1; iterator <= number_of_items; iterator++)
            {
                unsortedArray.add(Integer.parseInt(request.getParameter("item" + iterator)));
            }
            
            // Copy unsorted array to sorted array
            sortedArray = new ArrayList<Integer>(unsortedArray);
            
            // Sort the sorted array
            Collections.sort(sortedArray);
            
            
            try {
                // Generate XML - send unsorted array reference
                xml_gen(out);



                /*
                try
                {
                    if(val1 != null && val2 != null)
                    {
                        //xml_gen(val1, val2, out);
                    }
                    else
                    {
                        // Remove this test code eventually
                        //xml_gen("2", "6", out);
                    }
                }
                catch(Exception e)
                {
                    out.println("<error>");
                    out.println("ERROR ");
                    e.printStackTrace(out);
                    out.println("</error>");
                }*/
            } catch (Exception ex) {
                Logger.getLogger(data_xml.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            out.close();
        }
    }

    // This sample code can be deleted eventually
    protected void processRequestOld(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession(true);

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet data_xml</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet data_xml at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    /**
     * Process the input and generate the XML document to be returned.
     *
     * @param val1 First integer
     * @param val2 Second integer
     * @param out Stream to which the XML document will be written
     * @throws Exception
     */
    private void xml_gen(PrintWriter out) throws Exception
    {
        // Perform the calculation
        /*int v1 = Integer.parseInt(val1);
        int v2 = Integer.parseInt(val2);
        int sum = v1 + v2;
        String sumString = Integer.toString(sum);*/

        // Create the XML document
        DocumentBuilderFactory dbf =
            DocumentBuilderFactory.newInstance();
        //dbf.setValidating(false);
        DocumentBuilder db = dbf.newDocumentBuilder();
        //Document doc = db.parse(new File(filename));
        Element root;
        //root = doc.getDocumentElement();
        Document doc = db.newDocument();

        // Build the XML document by parts

        // Create the root element and set it up as the document root
        Element sorter = doc.createElement("sorter");
        root = sorter;
        doc.appendChild(root);

        // Unsorted elements
        Element unsorted_elements = doc.createElement("unsorted_numbers");
        sorter.appendChild(unsorted_elements);

        // Create children and add to unsorted elements
        for (Integer value : unsortedArray)
        {
            // Create elements
            Element item = doc.createElement("unsorted_item");
            Text item_value = doc.createTextNode(Integer.toString(value));
            
            // Add to xml document
            item.appendChild(item_value);
            unsorted_elements.appendChild(item);
        }
        
        // Sorted elements
        Element sorted_elements = doc.createElement("sorted_numbers");
        sorter.appendChild(sorted_elements);
        
        // Create children and add to sorted elements
        for (Integer value : sortedArray)
        {
            // Create elements
            Element item = doc.createElement("sorted_item");
            Text item_value = doc.createTextNode(Integer.toString(value));
            
            // Add to xml document
            item.appendChild(item_value);
            sorted_elements.appendChild(item);
        }
        
        
       /* Element operand1 = doc.createElement("operand1");
        Element operand2 = doc.createElement("operand2");
        Text operand1Value = doc.createTextNode(val1);
        Text operand2Value = doc.createTextNode(val2);
        operand1.appendChild(operand1Value);
        operand2.appendChild(operand2Value);
        operands.appendChild(operand1);
        operands.appendChild(operand2);

        Element results = doc.createElement("results");
        math_op.appendChild(results);
        Element add = doc.createElement("add");
        Text addValue = doc.createTextNode(sumString);
        add.appendChild(addValue);
        results.appendChild(add);*/

        // Output the XML document
        DOMSource domSource = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(out);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.METHOD,"xml");
        serializer.setOutputProperty(OutputKeys.ENCODING,"utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT,"yes");
        //serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"users.dtd");
        serializer.transform(domSource, streamResult);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
