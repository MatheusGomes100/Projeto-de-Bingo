# Bingo dos Ditados Populares

Aplicação desenvolvida em Java com interface gráfica para gestão e execução de partidas de bingo interativas em eventos com idosos. O sistema opera com um modelo híbrido: o sorteio eletrónico exibe a primeira metade de um ditado popular no telão, enquanto os participantes conferem a segunda metade impressa nas suas cartelas físicas.

## Sumário
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Arquitetura e Estrutura do Projeto](#arquitetura-e-estrutura-do-projeto)
- [Pré-requisitos e Execução](#pré-requisitos-e-execução)
- [Como Jogar](#como-jogar)
- [Autores](#autores)

---

## Tecnologias Utilizadas
- **Java 17+**
- **Maven** (Gestão de dependências e construção)
- **Swing** (Interface gráfica nativa)

---

## Arquitetura e Estrutura do Projeto

O código fonte está organizado de forma modular para separar as regras de negócio da interface gráfica:

- `BingoGame`: Controla as regras centrais do jogo, o fluxo de sorteio e a validação de vitória.
- `Sorteador`: Responsável por sortear os ditados sem repetição, mantendo o histórico ordenado.
- `Cartela`: Representa individualmente a cartela de cada participante e monitoriza o seu progresso.
- `ConfiguracaoJogoDialog`: Interface inicial para o registo e configuração dos jogadores por partida.
- `SomUtil`: Módulo de suporte para efeitos sonoros integrados.
- `JanelaPrincipal`: Janela principal que agrupa toda a experiência visual da aplicação.

> **Nota de Validação:** As 20 cartelas incluídas no diretório de salvamento foram rigorosamente conferidas contra o gabarito oficial em PDF, garantindo total integridade para utilização imediata.

---

## Pré-requisitos e Execução

Certifique-se de que possui o **Java 17 ou superior** instalado na sua máquina.

### Via Linha de Comando (Maven)
```bash
mvn clean package
java -jar target/BingoGame-1.0-SNAPSHOT.jar
```

### Via IDE (Ex: NetBeans / IntelliJ)
Basta abrir o projeto na sua IDE de preferência e executar a classe principal ou utilizar a opção de execução direta do projeto (`Run Project`).

---

## Como Jogar

1. Inicie a aplicação e clique em **NOVO JOGO**.
2. Informe a quantidade de participantes (entre 1 e 20).
3. Distribua as cartelas impressas sequencialmente (Cartela N 1, N 2, N 3...) e associe o nome de cada participante ao número correspondente no sistema.
4. Clique em **SORTEAR** (ou utilize a barra de **espaço**) para revelar a primeira metade do ditado na tela e proceda à leitura em voz alta para a plateia.
5. O sistema valida automaticamente quando um participante completa a cartela com base na segunda metade dos ditados e declara o vencedor na tela.
6. Utilize o painel lateral direito "Acompanhamento dos Jogadores" para monitorizar o progresso em tempo real de cada participante.
7. Pressione **F11** (ou clique no botão **TELA CHEIA**) para expandir a visualização caso esteja a projetar em monitores ou telões.

---

## Autores

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/MatheusGomes100">
        <img src="https://github.com/MatheusGomes100.png" width="80px" style="border-radius: 8px;" alt="Matheus Gomes"/><br>
        <sub><b>Matheus Gomes</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Mackaulyn">
        <img src="https://github.com/Mackaulyn.png" width="80px" style="border-radius: 8px;" alt="Mackaulyn Rodrigues"/><br>
        <sub><b>Mackaulyn Rodrigues</b></sub>
      </a>
    </td>
  </tr>
</table>
