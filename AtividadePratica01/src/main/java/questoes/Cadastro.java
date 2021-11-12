package questoes;

import java.io.*;
import java.util.Scanner;

public class Cadastro {
    private static PrintStream printStream; // metodos de saida
    public static void main(String [] args) throws IOException{

        printStream = new PrintStream(new FileOutputStream("C:\\Users\\Pedro\\AtividadePratica01\\src\\main\\resources\\listlivro.csv", true));
        Scanner ler = new Scanner(System.in);
        String entrada;
        Livro livro = new Livro();

            System.out.printf("Digite o ISBN do livro: ");
            entrada = ler.nextLine();
            livro.setIsbn(entrada);

            System.out.printf("Digite o titulo do livro: ");
            entrada = ler.nextLine();
            livro.setTitulo(entrada);

            System.out.printf("Digite a editora do livro: ");
            entrada = ler.nextLine();
            livro.setEditora(entrada);

            System.out.printf("Digite o ano de publicação do livro: ");
            entrada = ler.nextLine();
            livro.setAno_publicacao(entrada);

            printStream.println(livro.getIsnb()+", "+ livro.getTitulo() + ", "
                    + livro.getEditora() + ", " + livro.getAno_publicacao());

            printStream.close();

    }
}
