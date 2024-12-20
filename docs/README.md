# LDTS_T12_G01 - TETRIS

## Descrição do jogo

O nosso jogo é uma recriação do clássico Tetris com algumas novidades estratégicas. O objetivo do jogador é posicionar peças de diferentes formas e cores em um tabuleiro de 10x20 para completar e eliminar linhas horizontais. Cada linha eliminada concede pontos, enquanto que erros tornam o jogo cada vez mais desafiador devido ao acumular de blocos.

Os jogadores podem acumular pontos para desbloquear bônus que permitem eliminar linhas ou áreas específicas, prolongando o jogo e aumentando as possibilidades estratégicas.

Este projeto foi desenvolvido por **[Filipe Camacho(up202208040@fe.up.pt),Carolina Roque(up202305062@fe.up.pt),Raul Oliveira(up202303446@fe.up.pt)]** para a unidade curricular LDTS 2024/2025 na FEUP.

## Features Implementadas

- **GameModel** - Gere o jogo incluindo o tabuleiro, a pontuação(score), as peças(pieces) ativas, o sistema de bônus(bonus) e o estado de pausa do jogo.
- **Board** - Representado por uma matriz bidimensional 10x20 onde cada célula contém um caractere(char) e uma cor(TextColor). Possui funcionalidades que permitem manipular o estado do jopgo, como adicionar e mover peças, verificar o preenchimento de linhas completas e removê-las, e deteção do fim do jogo.
- **Gerador de peças aleatórias** - Sistema que fornece peças predefinidas (O, I, T, J, L) com cores distintas e permite gerar peças aleatórias, garantindo imprevisibilidade ao jogo.
- **Deteção de colisões de peças** - Verificação se as peças atingem outras no tabuleiro ou caso ultrapassem os limites do tabuleiro, impedindo movimentos inválidos.
- **PieceSelector** - Define as formas e cores das peças. Essas peças podem ser geradas aleatoriamente e o jogo possui cinco formas de peças predefinidas que são ultilizadas no jogo de mode a que o jogador consiga formar linhas completas.
- **Comandos** - Uso do Command Pattern para abstrair e encapsular ações como movimento, rotação e pausa do jogo.
- **Gestão de Estados** - Separação clara entre estados do jogo, como menu principal, jogo ativo, pausa e fim de jogo.
- **Movimento e Rotação de Peças** - As peças podem ser movidas para a esquerda, direita e para baixo. Além disso, as peças podem ser rotacionadas para a esquerda e para a direita. Desta forma, o jogador consegue controlar a posição da peça atual durante a sua queda.
- **Remoção de Linhas Completas** - Deteta e remove linha totalmente preenchidas e as linhas superiores deslocam-se para baixo e o jogador ganha pontos. Notifica sistemas associados, como a pontuação.
- **Sistema de pontuação** - O jogo possui um sistema de pontuação(score), que será atualizado à medida que cada linha é removida.
- **Sistema de Bônus**: Use pontos acumulados para desbloquear bônus que ajudam a limpar linhas ou áreas críticas.
- **Testes** - O códigos conta com vários testes automatizados que verificam desde a movimentação das peças até a remoção de linhas e cálculo da pontuação.

## Features planeadas

Todas as features planeadas foram implementadas com sucesso.


## Design

### Estrutura Geral
### Problema no Contexto

O jogo Tetris exige uma arquitetura modular e extensa para lidar com as diversas interações entre peças, tabuleiro e eventos do jogo. Para atender a esses requisitos, foram utilizados padrões de design adequados para resolver problemas como detecção de eventos, rotação de peças e controle de estados.

#### 