package com.aig.dcp.nextgen.core.services.servlets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletName;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;

import com.day.cq.dam.api.AssetManager;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;

@Component(service = Servlet.class, immediate = true)
@ServiceDescription(value = "DCP Page Service v3 - For Flexible Layout")	
@SlingServletPaths(value = { "/services/dcp/v3/pageservice" })
@SlingServletName(servletName = "DCP Page Service v3")
public class DCPPageService extends SlingSafeMethodsServlet {
	
	private static final long serialVersionUID = -7900461014203218252L;

	ResourceResolver resolver;
	
	Resource resource;
	
	ValueMap pageprops;
	
	/**
	 * Injecting the QueryBuilder dependency
	 */
	@Reference
	private QueryBuilder builder;
	
	/**
	 * Session object
	 */
	private Session session;
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/xml");
		
		String path = "/content/aig_apps/life/connext/en/dbp-demo-v3/public/login/jcr:content";
		
		
		/**
		 * Get resource resolver instance
		 */
		ResourceResolver resourceResolver = request.getResourceResolver();
		
		/**
		 * Adapting the resource resolver to the session object
		 */
		session = resourceResolver.adaptTo(Session.class);
		
		/**
		 * Map for the predicates
		 */
		Map<String, String> predicate = new HashMap<>();
		
		Resource currentResource = request.getResourceResolver().resolve(path);
		
		session = request.getResourceResolver().adaptTo(Session.class);
		pageprops = currentResource.getValueMap();
		String savedQuery = pageprops.get("savedQuery").toString();
		
		String[] splitQuery = savedQuery.split("\n");
		
		for(String splits : splitQuery)
		{
			String[] splitss = splits.split("=");
			predicate.put(splitss[0],splitss[1]);
		}

		Query query = builder.createQuery(PredicateGroup.create(predicate), session);
		SearchResult searchResult = query.getResult();
			
			for (Hit hit : searchResult.getHits()) {
				try {
					String paths = hit.getPath();
					
				} catch (RepositoryException e) {
					e.printStackTrace();
				}
			}

		String xmlString = createXML(request,pageprops );
		response.getWriter().write(xmlString);
		 
	}
	
	
	private String createXML(SlingHttpServletRequest request, ValueMap pageprops)
	{
		String xmlString = "";
		
		pageprops.get("lob");
		pageprops.get("security-api-lob");
		
		try {
	         StringWriter stringWriter = new StringWriter();

	         OutputStream out = new ByteArrayOutputStream(); 
	         
	         XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
	         XMLStreamWriter xMLStreamWriter =
	            xMLOutputFactory.createXMLStreamWriter(out,"US-ASCII" );
	   
	         xMLStreamWriter.writeStartDocument("US-ASCII", "1.0");
	         xMLStreamWriter.writeStartElement("cars");
	   
	         xMLStreamWriter.writeStartElement("supercars");	
	         xMLStreamWriter.writeAttribute("company", "Ferrari");
	      
	         xMLStreamWriter.writeStartElement("carname");
	         xMLStreamWriter.writeAttribute("type", "formula one");
	         xMLStreamWriter.writeCharacters(pageprops.get("jcr:primaryType").toString());
	         xMLStreamWriter.writeEndElement();

	         xMLStreamWriter.writeStartElement("carname");			
	         xMLStreamWriter.writeAttribute("type", "sports");
	         xMLStreamWriter.writeCharacters(pageprops.get("jcr:createdBy").toString());
	         xMLStreamWriter.writeEndElement();

	         xMLStreamWriter.writeEndElement();
	         xMLStreamWriter.writeEndDocument();

	         xMLStreamWriter.flush();
	         xMLStreamWriter.close();

	         xmlString = out.toString();
	         
	         AssetManager assetMgr = request.getResourceResolver().adaptTo(AssetManager.class);
	         
	         InputStream is = new ByteArrayInputStream(xmlString.getBytes());
	         assetMgr.createAsset("/content/dam/cars3.xml", is, "application/xml", true);
	         
	         stringWriter.close();
	         out.close();

	      } catch (XMLStreamException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
		return xmlString;
		
	}
	
}
