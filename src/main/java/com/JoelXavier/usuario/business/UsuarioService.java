package com.JoelXavier.usuario.business;

import com.JoelXavier.usuario.business.converter.UsuarioConverter;
import com.JoelXavier.usuario.business.dto.UsuarioDTO;
import com.JoelXavier.usuario.infraestructure.entity.Usuario;
import com.JoelXavier.usuario.infraestructure.exceptions.ConflictException;
import com.JoelXavier.usuario.infraestructure.exceptions.ResourceNotFoundException;
import com.JoelXavier.usuario.infraestructure.repository.UsuarioRepository;
import com.JoelXavier.usuario.infraestructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(usuario)
        );
    }
    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Este email já está cadastrado " + email);
            }
        }
        catch (Exception e) {
            throw new ConflictException("Este email já está cadastrado " + e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email" + email + "não foi encontrado"));
    }
    public void deletaUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }
    public  UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto) {
        //Aqui procuramos o email do usuário através do Token (Removendo a obrigatoriedade do email)
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        //Criptografia de senha
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

        //Procura os dados do usuário no banco de dados
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não encontrado"));

        //Mesclou os dados que recebemos na requisição DTO com os dados do banco de dados
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

        //Salvou os dados do usuário convertido e depois pegou o retorno e converteu para UsuárioDTO
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));

    }
}

