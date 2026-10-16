
package webservices;

import entities.UniteEnseignement;
import metiers.UniteEnseignementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ue")
public class UniteEnsRestApi {

    static UniteEnseignementBusiness helper =
            new UniteEnseignementBusiness();

    // Récupérer la liste des UE
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListUe() {
        return Response
                .status(200)
                .entity(helper.getListeUE())
                .build();
    }

    // Ajouter une nouvelle UE
    @Path("/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addUe(UniteEnseignement ue) {

        if (helper.addUniteEnseignement(ue)) {
            return Response
                    .status(201)
                    .entity("created")
                    .build();
        } else {
            return Response
                    .status(400)
                    .entity("error")
                    .build();
        }
    }

    @GET
    @Path("aff/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUeByCode(@PathParam("code") int code) {

        for (UniteEnseignement ue : helper.getListeUE()) {

            if (ue.getCode() == code) {
                return Response
                        .status(200)
                        .entity(ue)
                        .build();
            }
        }

        return Response
                .status(404)
                .entity("UE introuvable")
                .type(MediaType.TEXT_PLAIN)
                .build();
    }


    @DELETE
    @Path("remove/{code}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUe(@PathParam("code") int code) {

        UniteEnseignement ueTrouvee = null;

        for (UniteEnseignement ue : helper.getListeUE()) {
            if (ue.getCode() == code) {
                ueTrouvee = ue;
                break;
            }
        }

        if (ueTrouvee == null) {
            return Response.status(404)
                    .entity("UE introuvable")
                    .build();
        }

        boolean supprimee = helper.getListeUE().remove(ueTrouvee);

        if (supprimee) {
            return Response.status(200)
                    .entity("UE supprimee avec succes")
                    .build();
        }

        return Response.status(500)
                .entity("Erreur lors de la suppression")
                .build();
    }

    @PUT
    @Path("modifier/{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response modifierUe(
            @PathParam("code") int code,
            UniteEnseignement nouvelleUe) {

        for (UniteEnseignement ue : helper.getListeUE()) {

            if (ue.getCode() == code) {

                ue.setDomaine(nouvelleUe.getDomaine());
                ue.setResponsable(nouvelleUe.getResponsable());
                ue.setCredits(nouvelleUe.getCredits());
                ue.setSemestre(nouvelleUe.getSemestre());

                return Response.status(200)
                        .entity("UE modifiee avec succes")
                        .build();
            }
        }

        return Response.status(404)
                .entity("UE introuvable")
                .build();
    }

}
