/**
 * 
 */
package uk.bl;

import java.io.File;
import java.io.FileNotFoundException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.tika.mime.MediaType;

/**
 * @author Peter May (The British Library)
 */
@Path("/rest")
public class TikaResource {
	
	private TikaWrapper tika;
	
	public TikaResource(){
		tika = TikaWrapper.getTika();
	}
	
//	@GET 
//	@Path("/images/")
//	@Produces("image/*")
//	public Response getImage(@QueryParam("file") String file) {
//		System.out.println("file: "+file);
//		
//		File f = new File(file);
//		if (!f.exists()) {
//			throw new WebApplicationException(404);
//		}
//
//		String mt = new MimetypesFileTypeMap().getContentType(f);
//		return Response.ok(f, mt).build();
//	}

	/**
	 * Retrieves the mime-type for the specified file
	 * @param file
	 * @return
	 */
	@GET
	@Path("/mime")
	public Response getMimeType(@QueryParam("file") String file){
		MediaType mime;
		try {
			mime = tika.getMimeType(new File(file));
		} catch (FileNotFoundException e) {
			throw new WebApplicationException(404);
		}
		
		String output = ""+mime;
		return Response.status(200).entity(output).build();
	}
	
	/**
	 * Parses a file using Tika and returns the result
	 * @param file
	 * @return
	 */
	@GET
	@Path("/parse")
	public Response parseFile(@QueryParam("file") String file){
		String response = "";
		try {
			response = tika.parse(new File(file));
		} catch (FileNotFoundException e) {
			throw new WebApplicationException(404);
		}
		return Response.status(200).entity(response).build();
	}

}
