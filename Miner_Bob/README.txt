Entrega 1 -------------------------------
Implementado com funções em uma única classe.

Entrega 2 -------------------------------
Creio que fiz algumas das implementações errado.
Especificamente eu não coloquei parte dos executes do states na classe dos Bob como demonstrado nos slides.
Pretendo consertar esse problema para a próxima entrega caso exista.

Entrega 3 ------------------------
Implementação do Singleton
Implementação das generics no State
Implementação do NPC Billy
Mudanças em todos os States, encapsulando condições e efeitos em funções dentro dos NPCs ao invés de dentro das States.

Entrega 4 -----------------------------------

Implementação do formato State no Billy
Implementação da StateMachine
Implementação do Sistema de Mensagens
Implementação do State TrabalhoFalso

Notas: Apesar estar funcionando, o sistema está fazendo updates de uma forma que não intercala as atividades de Bob e Billy(Bob faz tudo até voltar pra casa então billy faz todas as suas iterações antes de retornar ao Bob).
e todas as vezes que o State VisitarBanheiro é chamado se cria um novo thread no processamento que causa as várias mensagens de final de programa.

