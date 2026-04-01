package br.ifmg.produto1_2026.util;

import br.ifmg.produto1_2026.entities.Usuario;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoEmail implements Notificador {

    private boolean caixaAlta;
    private String servidorSmpt;


    public NotificacaoEmail(String servidorSmpt) {
        System.out.println("NotificacaoEmail criado com sucesso!");
        this.servidorSmpt = servidorSmpt;
    }
//10 - iniciando com injeção de dependencia
    public void notificar(Usuario usuario, String mensagem) {

        if (caixaAlta) {
            mensagem = mensagem.toUpperCase();
        }

        System.out.printf("Notifificando %s através do email %s no servidor %s: %s\n",
                usuario.getNome(), usuario.getEmail(), servidorSmpt, mensagem);
    }

}
