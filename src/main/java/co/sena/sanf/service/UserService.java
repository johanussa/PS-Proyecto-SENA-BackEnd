package co.sena.sanf.service;

import co.sena.sanf.domain.Meta;
import co.sena.sanf.domain.User;
import co.sena.sanf.domain.UserRegistration;
import co.sena.sanf.helper.exceptions.DRException;
import co.sena.sanf.repository.UserRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import io.github.cdimascio.dotenv.Dotenv;
import io.quarkus.panache.common.Sort;
import io.vertx.core.http.HttpServerRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.jboss.logging.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserService {

    @Inject Logger LOG;
    @Inject MongoClient mongoClient;
    @Inject UserRepository userRepository;
    @Inject jakarta.inject.Provider<HttpServerRequest> httpServerRequestProvider;

    public UserRegistration getUserRegistered(String idUser) {
        LOG.infof("@getUserRegistered API > Inicia servicio para obtener un usuario registrados");
//        User fndUser = userRepository.findById(new ObjectId());
        return null;
    }

    public List<UserRegistration> getAllUsers() throws DRException {
        LOG.infof("@getUsers API > Inicia servicio para obtener lista de usuarios registrados");
        List<UserRegistration> listUsers = userRepository.listAll(Sort.descending("meta.fechaCreacion"));

        verifyListEmpty(listUsers);
        LOG.infof("@getUsers API > Finaliza servicio para obtener lista de usuarios registrados");
        return listUsers;
    }

    public void addOneUser(User user) throws DRException, UnknownHostException {
        LOG.infof("@getUsers API > Inicia servicio para registo de usuario en base de datos con data: %s", user);
        verifyUserExistsToAdd(user.getDocumentNumber());
        UserRegistration newRegister = UserRegistration.builder()
            .meta(
                Meta.builder()
                    .creationDate(LocalDateTime.now())
                    .source(httpServerRequestProvider.get().absoluteURI())
                    .ipAddress(InetAddress.getLocalHost().getHostAddress())
                    .build()
            )
            .data(user)
            .build();
        userRepository.persist(newRegister);
        LOG.infof("@getUsers API > El registro del usuario fue exitoso");
        LOG.infof("@getUsers API > Finaliza servicio para registo de usuario en base de datos con data: %s", user);
    }

    public void updateOneUser(User user) throws DRException {
        String idUser = user.getDocumentNumber();
        Document dataUser = (Document) getCollection().find(new Document("data.numDocumento", idUser)).first();

        userExist(dataUser != null, idUser);
        Document dataUp = new Document();
        assert dataUser != null;

        Document data = dataUser.get("data", Document.class);
        Document meta = dataUser.get("meta", Document.class);

        data.put("nombre", user.getName());
        data.put("apellido", user.getLastName());
        data.put("tipoDocumento", user.getDocumentType());
        data.put("correo", user.getEmail());
        data.put("password", user.getPassword());
        data.put("activo", user.getStatus());
        meta.put("fechaActualizacion", addUpdateDate(meta));

        dataUp.put("meta", meta);
        dataUp.put("data", data);
        getCollection().updateOne(new Document("data.numDocumento", idUser), new Document("$set", dataUp));
    }

    public void deleteOneUser(String idUser) throws DRException {
        Optional<UserRegistration> dataUser = userRepository.searchUserRegistration(idUser);
        userExist(dataUser.isPresent(), idUser);
        userRepository.deleteUserRegistration(idUser);
    }
    private List<LocalDateTime> addUpdateDate(Document meta) {
        List<LocalDateTime> datesAfter = meta.get("fechaActualizacion", List.class);
        List<LocalDateTime> list = datesAfter.isEmpty() ? new ArrayList<>() : datesAfter;
        if (list.size() == 5) list.remove(0);
        list.add(LocalDateTime.now());
        return list;
    }
    private MongoCollection<?> getCollection(){
        Dotenv dotenv = Dotenv.configure().load();
        return mongoClient.getDatabase(dotenv.get("DATABASE")).getCollection(dotenv.get("COLLECTION"));
    }

    private void verifyListEmpty(List<UserRegistration> list) throws DRException {
        if (list.isEmpty()) {
            LOG.infof("@verifyListEmpty, No se encontraron registros de usuarios en la base de datos");
            throw new DRException(
                Response.Status.NOT_FOUND.getStatusCode(),
                "No se encontraron registros de usuarios en la base de datos"
            );
        }
        LOG.infof("@verifyListEmpty, La lista de usuarios se obtuvo correctamente");
    }
    private void verifyUserExistsToAdd(String idUser) throws DRException {
        if (userRepository.searchUserRegistration(idUser).isPresent()) {
            LOG.errorf("@verifyUserExists, El usuario de id: %s ya se encuetra registrado", idUser);
            throw new DRException(
                Response.Status.CONFLICT.getStatusCode(),
                "El usuario de id: " + idUser + " ya se encuentra registrado"
            );
        }
    }
    private void userExist(boolean findUserData, String idUser) throws DRException {
        if (findUserData) return;
        LOG.errorf("@userExist, El usuario de id: %s NO se encuetra registrado", idUser);
        throw new DRException(
            Response.Status.NOT_FOUND.getStatusCode(),
            "El usuario de id: " + idUser + " NO se encuetra registrado"
        );
    }
}
