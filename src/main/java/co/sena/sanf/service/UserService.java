package co.sena.sanf.service;

import co.sena.sanf.domain.Meta;
import co.sena.sanf.domain.user.User;
import co.sena.sanf.domain.user.UserRegister;
import co.sena.sanf.helper.exceptions.DRException;
import co.sena.sanf.repository.UserRepository;
import io.quarkus.panache.common.Sort;
import io.vertx.core.http.HttpServerRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.bouncycastle.crypto.generators.OpenBSDBCrypt;
import org.jboss.logging.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserService {

    @Inject Logger LOG;
    @Inject UserRepository userRepository;
    @Inject jakarta.inject.Provider<HttpServerRequest> httpServerRequestProvider;

    public UserRegister getUserRegistered(String idUser) throws DRException {
        LOG.infof("@getUserRegistered API > Inicia servicio para obtener un usuario registrado");
        Optional<UserRegister> findUser = userRepository.searchUserRegistration(idUser);
        userExist(findUser.isPresent(), idUser);
        LOG.infof("@getUserRegistered API > Finaliza servicio para obtener un usuario registrado");
        return findUser.orElse(null);
    }

    public List<UserRegister> getAllUsers() throws DRException {
        LOG.infof("@getUsers API > Inicia servicio para obtener lista de usuarios registrados");
        List<UserRegister> listUsers = userRepository.listAll(Sort.descending("meta.fechaCreacion"));

        verifyListEmpty(listUsers);
        LOG.infof("@getUsers API > Finaliza servicio para obtener lista de usuarios registrados");
        return listUsers;
    }

    public void addOneUser(UserRegister user) throws DRException, UnknownHostException {
        LOG.infof("@getUsers API > Inicia servicio para registo de usuario en base de datos con data: %s", user);
        verifyUserExistsToAdd(user.getData().getDocumentNumber());
        user.getData().setPassword(encryptPassword(user.getData().getPassword()));
        user.getData().setStatus(false);
        UserRegister newRegister = UserRegister.builder()
            .meta(
                Meta.builder()
                    .creationDate(LocalDateTime.now())
                    .source(httpServerRequestProvider.get().absoluteURI())
                    .ipAddress(InetAddress.getLocalHost().getHostAddress())
                    .build()
            )
            .data(user.getData())
            .build();
        userRepository.persist(newRegister);
        LOG.infof("@getUsers API > El registro del usuario fue exitoso");
        LOG.infof("@getUsers API > Finaliza servicio para registo de usuario en base de datos con data: %s", user);
    }

    public void updateOneUser(UserRegister user) throws DRException {
        LOG.infof("@updateOneUser API > Inicia servicio para actualizar un usuario registrado con la data: " +
            "%s", user);
        String idUser = user.getData().getDocumentNumber();
        Optional<UserRegister> dataUser = userRepository.searchUserRegistration(idUser);
        userExist(dataUser.isPresent(), idUser);

        User data = dataUser.orElseThrow().getData();
        Meta meta = dataUser.orElseThrow().getMeta();

        data.setName(user.getData().getName());
        data.setLastName(user.getData().getLastName());
        data.setDocumentType(user.getData().getDocumentType());
        data.setEmail(user.getData().getEmail());
        data.setPassword(encryptPassword(user.getData().getPassword()));
        data.setStatus(user.getData().getStatus());
        meta.setUpdateDate(addUpdateDate(meta));

        userRepository.update(dataUser.get());
        LOG.infof("@updateOneUser API > El usuario de id: %s fue actualizado con exito", idUser);
        LOG.infof("@updateOneUser API > Finaliza servicio para actualizar un usuario registrado");
    }

    public void deleteOneUser(String idUser) throws DRException {
        LOG.infof("@deleteOneUser API > Inicia servicio para eliminar un usuario registrado de id: %s", idUser);
        Optional<UserRegister> dataUser = userRepository.searchUserRegistration(idUser);
        userExist(dataUser.isPresent(), idUser);

        userRepository.deleteById(dataUser.orElseThrow().getId());
        LOG.infof("@deleteOneUser API > El usuario de id: %s fue eliminado exitosamente", idUser);
        LOG.infof("@deleteOneUser API > Finaliza servicio para eliminar un usuario registrado de id: %s", idUser);
    }
    private List<LocalDateTime> addUpdateDate(Meta meta) {
        LOG.infof("@addUpdateDate API > Inicia servicio para agregar fecha de actualización al registro meta");
        List<LocalDateTime> datesAfter = meta.getUpdateDate();
        List<LocalDateTime> list = datesAfter == null ? new ArrayList<>() : datesAfter;
        if (list.size() == 5) list.remove(0);
        list.add(LocalDateTime.now());
        LOG.infof("@addUpdateDate API > Finaliza servicio para agregar fecha de actualización al registro meta");
        return list;
    }

    private void verifyListEmpty(List<UserRegister> list) throws DRException {
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
    private String encryptPassword(String pass) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return OpenBSDBCrypt.generate(pass.toCharArray(), salt, 12);
    }
}
