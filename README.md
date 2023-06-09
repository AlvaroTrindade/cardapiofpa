Problema - Definição de Cardápio

Metodologia de Resolução: Algorítmo Gulogo
Para resolução do problema abordado foi utilizada a metodologia de resolução definida como gulosa, visto que baseado
em uma condição definida, o mesmo considera a ótima local. Nese exemplo a regra utilizada foi sempre escolher o prato
de maior proporção da rodada de escolha.

Metodologia de Resolução: Programação Dinâmica
A Programação Dinâmica é uma técnica de resolução de problemas que envolvem subestrutura ótima e sobreposição de 
subproblemas. Ela é baseada na ideia de decompor um problema em subproblemas menores, resolver esses subproblemas de 
forma recursiva e, em seguida, combinar as soluções dos subproblemas para obter a solução global.

Detalhamento adicional:
1.	Como esse problema pode ser modelado para o paradigma guloso?

Esse problema pode ser modelado para o paradigma guloso considerando o seguinte critério de seleção: seleciona os pratos
com maior valor de ν/c, garantindo que sejam escolhidos pratos com maior lucro em relação ao custo.
Ao iterar sobre os pratos ordenados, o algoritmo sempre escolhe o prato com maior ν/c que ainda caiba no orçamento 
disponível. Essa abordagem maximiza o lucro final dentro das restrições do orçamento.

2.	Seu algoritmo guloso apresenta a solução ótima? Por que?

O algoritmo guloso não garante sempre a solução ótima global, pois o mesmo considera o melhor para aquela situação. 
Um exemplo no contexto seria quando o orçamento é estourado devido a escolha constante da maior proporção local.

3.	Como esse problema pode ser modelado para o paradigma de programação dinâmica?

A abordagem de programação dinâmica para resolver esse problema envolve a criação de uma tabela de memorização que 
armazena o máximo de lucro alcançável para diferentes combinações de dias e orçamento. Inicialmente, a tabela é 
preenchida com valores zero. Em seguida, percorremos os pratos disponíveis e, para cada prato, percorremos a tabela de
memorização de forma decrescente, verificando se o custo do prato é menor ou igual ao orçamento atual. Se for, 
calculamos o lucro potencial considerando as restrições de repetição de pratos seguidos. Comparamos o lucro potencial
com o valor atualmente armazenado na tabela e atualizamos a entrada se o lucro potencial for maior. No final do 
processo, o valor máximo de lucro alcançável é armazenado na tabela. Para determinar o cardápio que alcança o lucro
máximo, percorremos novamente a tabela de memorização, comparando os valores com as entradas anteriores e adicionando
os pratos selecionados a uma lista. Essa lista de pratos selecionados representa o cardápio que maximiza o lucro dentro
das restrições do problema. A abordagem de programação dinâmica leva em consideração as restrições do número máximo de
dias e a restrição de repetição de pratos seguidos, garantindo uma solução ótima para o problema.

4.	Discuta a subestrutura ótima e a sobreposição dos problemas.

5.	Se algum algoritmo clássico foi adaptado para resolver o problema, qual foi ele?

