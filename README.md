# Bingo dos Ditados Populares

Jogo de bingo para eventos com idosos, baseado em ditados populares:
metade do ditado aparece no telão/computador (sorteada) e a outra metade
está impressa nas cartelas de papel de cada participante.

## Como executar

Pré-requisito: Java 17 ou superior instalado.

```
mvn clean package
java -jar target/BingoGame-1.0-SNAPSHOT.jar
```

Ou, no NetBeans, basta abrir o projeto e rodar normalmente (Run Project).

## Como jogar

1. Clique em **NOVO JOGO**.
2. Informe quantos participantes vão jogar (1 a 20).
3. Na tela seguinte, entregue as cartelas impressas na ordem numerada
   (Cartela Nº 1, Nº 2, Nº 3...) e digite o nome de cada pessoa ao lado
   do número correspondente. Isso garante que o sistema saiba exatamente
   qual cartela física pertence a cada participante.
4. Clique em **SORTEAR** (ou aperte a barra de **espaço**) para revelar,
   um de cada vez, a primeira metade de um ditado. Leia em voz alta para
   a plateia.
5. Quem tiver a segunda metade de todos os ditados sorteados na sua
   cartela grita BINGO! O sistema detecta automaticamente quando alguém
   completa a cartela e mostra o vencedor na tela.
6. O painel "Acompanhamento dos Jogadores", à direita, mostra o progresso
   de cada cartela em tempo real — útil para a equipe acompanhar quem
   está mais perto de ganhar.
7. Aperte **F11** (ou o botão "TELA CHEIA") para maximizar a janela na
   hora de projetar em uma TV ou telão.

## Estrutura do projeto

- `BingoGame` — regras do jogo (sorteio, cartelas, verificação de vitória).
- `Sorteador` — sorteia os ditados sem repetição e guarda a ordem sorteada.
- `Cartela` — representa a cartela de cada participante e seu progresso.
- `ConfiguracaoJogoDialog` — tela de cadastro de jogadores no início de cada partida.
- `SomUtil` — efeitos sonoros simples (sem depender de arquivos de áudio).
- `JanelaPrincipal` — interface gráfica.

As 20 cartelas salvas em `BingoGame.CARTELAS_SALVAS` foram conferidas,
ditado por ditado, contra o PDF `BINGO DITADOS POPULARES` — todas batem
certinho com o gabarito impresso.
