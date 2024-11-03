package co.edu.uniquindio.peluqueria.servicios.implementacion;

import co.edu.uniquindio.peluqueria.config.JWTUtils;
import co.edu.uniquindio.peluqueria.dto.ClienteDTOs.CrearClienteDTO;
import co.edu.uniquindio.peluqueria.dto.ClienteDTOs.EditarClienteDTO;
import co.edu.uniquindio.peluqueria.dto.ClienteDTOs.LoginDTO;
import co.edu.uniquindio.peluqueria.dto.TokenDTOs.TokenDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Cliente;
import co.edu.uniquindio.peluqueria.model.enums.EstadoCuenta;
import co.edu.uniquindio.peluqueria.model.enums.Rol;
import co.edu.uniquindio.peluqueria.repositorios.ClienteRepo;
import co.edu.uniquindio.peluqueria.servicios.interfaces.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClienteServicioImpl implements ClienteServicio {

    @Autowired
    private ClienteRepo clienteRepo;

    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;

    public ClienteServicioImpl(BCryptPasswordEncoder passwordEncoder, JWTUtils jwtUtils) {
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public void registrarCliente(CrearClienteDTO nuevoClienteDTO) throws Exception {
        System.out.println(nuevoClienteDTO.correo());
        System.out.println(nuevoClienteDTO.id());
        System.out.println(nuevoClienteDTO.telefono());
        System.out.println(nuevoClienteDTO.password());
        if (clienteRepo.existsById(nuevoClienteDTO.id())) {
            throw new Exception("El ID ya está registrado.");
        }

        // Continuar con el registro del cliente
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setId(nuevoClienteDTO.id());
        nuevoCliente.setNombre(nuevoClienteDTO.nombre());
        nuevoCliente.setTelefono(nuevoClienteDTO.telefono());
        nuevoCliente.setEmail(nuevoClienteDTO.correo());
        nuevoCliente.setPassword(nuevoClienteDTO.password());

        clienteRepo.save(nuevoCliente);
    }


    @Override
    public Cliente editarCliente(EditarClienteDTO editarClienteDTO) throws Exception {
        Optional<Cliente> clienteOpt = clienteRepo.findById(editarClienteDTO.id());
        if (clienteOpt.isEmpty()) {
            throw new Exception("El cliente no existe.");
        }

        Cliente cliente = clienteOpt.get();
        cliente.setNombre(editarClienteDTO.nombre());
        cliente.setTelefono(editarClienteDTO.telefono());
        cliente.setEmail(editarClienteDTO.correo());

        return clienteRepo.save(cliente);
    }

    @Override
    public void eliminarCliente(String id) throws Exception {
        if (!clienteRepo.existsById(id)) {
            throw new Exception("El cliente no existe.");
        }
        clienteRepo.deleteById(id);
    }

    @Override
    public Cliente obtenerClientePorId(String id) throws Exception {
        return clienteRepo.findById(id).orElseThrow(() -> new Exception("El cliente no fue encontrado."));
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepo.findAll();
    }

    @Override
    public TokenDTO iniciarSesion(LoginDTO loginDTO) throws Exception {

        Cliente cuenta = obtenerPorEmail(loginDTO.correo());

        System.out.println("credenciales:" + cuenta.getEmail() + " PS:" + cuenta.getPassword()+"--");
        System.out.println("credenciales ingresadas:" + loginDTO.correo() + " PS:" + loginDTO.password()+"--");

        if (!loginDTO.password().equals(cuenta.getPassword())) {
            throw new Exception("La contraseña es incorrecta");
        }

        if (loginDTO.correo().equals("admin@gmail.com") && loginDTO.password().equals("admin")) {
            Map<String, Object> mapAdmin = construirClaimsAdmin(cuenta);
            return new TokenDTO(jwtUtils.generarToken(cuenta.getEmail(), mapAdmin));
        }else{
            Map<String, Object> mapClient = construirClaims(cuenta);
            return new TokenDTO(jwtUtils.generarToken(cuenta.getEmail(), mapClient));
        }

    }
    private Map<String, Object> construirClaims(Cliente cuenta) {
        return Map.of(
                "rol", Rol.CLIENTE,
                "nombre", cuenta.getNombre(),
                "id", cuenta.getId()
        );
    }
    private Map<String, Object> construirClaimsAdmin(Cliente cuenta) {
        return Map.of(
                "rol", Rol.ADMINISTRADOR,
                "nombre", cuenta.getNombre(),
                "id", cuenta.getId()
        );
    }
    private Cliente obtenerPorEmail(String correo) throws Exception {
        Optional<Cliente> cuentaOptional = clienteRepo.buscarCuentaPorCorreo(correo);

        if (cuentaOptional.isEmpty()) {
            throw new Exception("La cuenta con el correo: " + correo + " no existe");
        }
        System.out.println("Hay cuenta");
        return cuentaOptional.get();
    }
}
