package com.JoelXavier.usuario.business.converter;

import com.JoelXavier.usuario.business.dto.EnderecoDTO;
import com.JoelXavier.usuario.business.dto.TelefoneDTO;
import com.JoelXavier.usuario.business.dto.UsuarioDTO;
import com.JoelXavier.usuario.infraestructure.entity.Endereco;
import com.JoelXavier.usuario.infraestructure.entity.Telefone;
import com.JoelXavier.usuario.infraestructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {
    public Usuario paraUsuario(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefones(usuarioDTO.getTelefones()))
                .build();

    }
    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOS) {
        return enderecoDTOS.stream() .map(this::paraEndereco).toList();
    }


    public Endereco paraEndereco(EnderecoDTO enderecoDTO) {
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .cep(enderecoDTO.getCep())
                .numero(enderecoDTO.getNumero())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .estado(enderecoDTO.getEstado())
                .build();
    }
    public List<Telefone> paraListaTelefones(List<TelefoneDTO> telefoneDTOS) {
        return telefoneDTOS.stream() .map(this::paraTelefone).toList();
    }
    public Telefone paraTelefone(TelefoneDTO telefoneDTO) {
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

        public UsuarioDTO paraUsuarioDTO(Usuario usuarioDTO) {
            return UsuarioDTO.builder()
                    .nome(usuarioDTO.getNome())
                    .email(usuarioDTO.getEmail())
                    .senha(usuarioDTO.getSenha())
                    .enderecos(paraListaEnderecoDTO(usuarioDTO.getEnderecos()))
                    .telefones(paraListaTelefoneDTO(usuarioDTO.getTelefones()))
                    .build();

        }
        public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecoDTOS) {
            return enderecoDTOS.stream() .map(this::paraEnderecoDTO).toList();
        }


        public EnderecoDTO paraEnderecoDTO(Endereco enderecoDTO) {
            return EnderecoDTO.builder()
                    .rua(enderecoDTO.getRua())
                    .cep(enderecoDTO.getCep())
                    .numero(enderecoDTO.getNumero())
                    .cidade(enderecoDTO.getCidade())
                    .complemento(enderecoDTO.getComplemento())
                    .estado(enderecoDTO.getEstado())
                    .build();
        }
        public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> telefoneDTOS) {
            return telefoneDTOS.stream() .map(this::paraTelefoneDTO).toList();
        }
        public TelefoneDTO paraTelefoneDTO(Telefone telefoneDTO) {
            return TelefoneDTO.builder()
                    .numero(telefoneDTO.getNumero())
                    .ddd(telefoneDTO.getDdd())
                    .build();
        }


}
