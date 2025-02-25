package com.example.teste_agro_e;

import com.example.teste_agro_e.services.FipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Teste implements CommandLineRunner {
    @Autowired
    private FipeService fipeService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Aplicação iniciada! Executando código de inicialização...");
        System.out.println("Salvando as marcas disponiveis no cache...");
        try{
            fipeService.catchBrands();
            System.out.println("Marcas salvas com sucesso.");
        }catch(Exception e){
            System.out.println("Falha ao salvar as marcas: " + e.getMessage());
        }
    }
}
