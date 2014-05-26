package com.mycomp.execspec.jiraplugin.rest;

import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.mycomp.execspec.jiraplugin.dto.autocomplete.AutoCompleteDTO;
import com.mycomp.execspec.jiraplugin.dto.autocomplete.AutoCompletePayload;
import com.mycomp.execspec.jiraplugin.service.AutoCompleteService;
import org.apache.commons.lang.Validate;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Dmytro on 5/15/2014.
 */
@Path("/autocomplete")
public class AutoCompleteResource {

    private AutoCompleteService autoCompleteService;

    public AutoCompleteResource(AutoCompleteService autoCompleteService) {
        this.autoCompleteService = autoCompleteService;
    }

    @POST
    @AnonymousAllowed
    @Path("/{projectKey}")
    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
    public Response autoComplete(@PathParam("projectKey") String projectKey, String input) {

        Validate.notNull(input); // we can actually do auto complete for empty input but not null

        List<AutoCompleteDTO> autoCompleteDTOs = autoCompleteService.autoComplete(projectKey, input);

        Response response;
        if (!autoCompleteDTOs.isEmpty()) {
            AutoCompletePayload payload = new AutoCompletePayload(autoCompleteDTOs);
            response = Response.ok(payload, MediaType.APPLICATION_JSON).build();
        } else {
            response = Response.noContent().build();
        }
        return response;
    }

}
