
"""Afficher le graphe des implications"""
import math
import matplotlib
matplotlib.use('TkAgg')  # Utilisez un backend compatible avec votre environnement, comme 'TkAgg' ou 'Qt5Agg'
import networkx as nx
import matplotlib.pyplot as plt


class Graph:
    def __init__(self):
        self.nodes = {}
        self.edges = {}
    def add_node(self, node):
        if node not in self.nodes:
            self.nodes[node] = True
    def add_edge(self, from_node, to_node):
        if from_node not in self.nodes or to_node not in self.nodes:
            raise ValueError("Both nodes must be added to the graph before adding an edge.")
        if from_node not in self.edges:
            self.edges[from_node] = []
        self.edges[from_node].append(to_node)
    def __str__(self):
        return f"Nodes: {list(self.nodes.keys())}, Edges: {self.edges}"

data = [(1, 2, 0, 1, 1, 0),
        (1, 3, 1, 0, 0, 0),
        (2, 1, 0, 0, 1, 1),
        (2, 2, 0, 1, 1, 0),
        (2, 4, 1, 0, 0, 1),
        (3, 1, 0, 1, 1, 0),
        (3, 3, 1, 0, 0, 1),
        (3, 4, 1, 0, 1, 0),
        (4, 1, 1, 0, 0, 1),
        (4, 2, 1, 1, 0, 0),
        (4, 3, 0, 1, 1, 0)]
"""data = [(1, 3, 1, 1, 1, 0),
        (2, 1, 1, 1, 0, 1),
        (2, 2, 0, 1, 1, 0),
        (3, 1, 1, 1, 1, 0),
        (3, 2, 1, 1, 0, 1),
        (3, 3, 1, 0, 0, 1),
        (2, 4, 0, 1, 1, 1),
        (4, 3, 1, 0, 1, 1),
        (4, 1, 1, 1, 1, 0),
        (4, 4, 0, 1, 1, 1)
    ]

data =     [(1, 2, 1, 1, 0, 1),
            (1, 3, 1, 0, 1, 0),
            (2, 1, 1, 0, 0, 1),
            (2, 2, 0, 1, 1, 0),
            (3, 1, 0, 1, 1, 0),
            (3, 2, 1, 1, 0, 1),
            (3, 3, 1, 0, 0, 1)]

data = [
    (1, 3,0, 0, 0, 1),
    (2, 1,1, 0, 1, 0),
    (2, 4,0, 1, 1, 0),
    (3, 2,0, 0, 1, 1),
    (3, 3,1, 0, 0, 0),
    (4, 1,0, 1, 0, 0),
    (4, 4,0, 1, 1, 0)
]"""
with open("numero.txt", "w") as f:
        for item in data:
            f.write(" ".join(map(str, item)) + " \n")

def lire_fichier_ampoules(chemin_fichier):
    """A partirr du fichier je crée un dico"""
    # dans chaque ampoule YiXj je mets les combinaisons
    ampoules = {}
    with open(chemin_fichier, 'r') as f:
        for ligne in f:
            valeurs = ligne.strip().split()
            key = (int(valeurs[0]), int(valeurs[1]))
            value =valeurs[2:]
            ampoules[key] = value
    return   ampoules


def ones_zeros_to_VF_in_dico(ampoules):
    """Je converti les 1000 , 0010 en VV , FV etc.."""
    for key, value in ampoules.items():
            result = []
            if int(value[0]) == 1:
                result.append("VV")
            if int(value[1]) == 1:
                result.append("VF")
            if int(value[2]) == 1:
                result.append("FV")
            if int(value[3]) == 1:
                result.append("FF")
            ampoules[key]= result
    return ampoules

print(ones_zeros_to_VF_in_dico(lire_fichier_ampoules("numero.txt")), "Voici à quoi ressemble le dictionnaire avec pour valeur les vrais ou faux ")
dico_ampoules_Vrai_faux = ones_zeros_to_VF_in_dico(lire_fichier_ampoules("numero.txt")) # ma variable qui stocke le dictionnaire
print(dico_ampoules_Vrai_faux, "dico ampoiule vrai vfaux ")

def create_graph(dico):
    """Je crée le graphe"""
    graph = Graph()
    for key, values in dico.items():
        for value in values:
            if len(values) == 1 :
                # Création des nœuds
                source_node = f"(Y{key[0]}, {value[0]})"
                target_node = f"(X{key[1]}, {value[1]})"
                # Ajout des nœuds au graphe
                graph.add_node(source_node)
                graph.add_node(target_node)
                # Ajout de l'arête entre les nœuds
                graph.add_edge(source_node, target_node)
                graph.add_edge(target_node, source_node)
            elif len(values) == 2 :
                # Création des nœuds
                source_node = f"(Y{key[0]}, {value[0]})"
                target_node = f"(X{key[1]}, {value[1]})"
                # Ajout des nœuds au graphe
                graph.add_node(source_node)
                graph.add_node(target_node)
                # Ajout de l'arête entre les nœuds
                if values[0] == "VV" and values[1] == "FV":
                    graph.add_edge(source_node, target_node)        #on peut pas inverser

                elif values[0] == "VV" and values[1] == "VF":
                    graph.add_edge(target_node, source_node)        #on inverse

                elif  values[0] == "VV" and  values[1] == "FV" :    #on peut ppas inverser
                    graph.add_edge(source_node, target_node)

                elif values[0] == "VV" and values[1] == "FF":
                    graph.add_edge(source_node, target_node)
                    graph.add_edge(target_node, source_node)        #on inverse

                elif  values[0] == "VF" and  values[1] == "FV" :
                    graph.add_edge(source_node, target_node)        #on inverse
                    graph.add_edge(target_node, source_node)

                elif  values[0] == "VF" and  values[1] == "FF" :    #on peut ppas inverser
                    graph.add_edge(source_node, target_node)

                elif  values[0] == "FV" and  values[1] == "FF" :    #on peut ppas inverser
                    graph.add_edge(target_node, source_node)
            elif len(values) ==3 :
                    source_node = f"(Y{key[0]}, {value[0]})"
                    target_node = f"(X{key[1]}, {value[1]})"
                    # Ajout des nœuds au graphe
                    graph.add_node(source_node)
                    graph.add_node(target_node)
                    # Ajout de l'arête entre les nœuds
                    if values[0] == "VV" and  values[1] == "VF" and values[2] == "FV":
                        if value == "FV":
                            graph.add_edge(source_node, target_node)
                        if value == "VF" :
                            graph.add_edge(target_node, source_node)
                    elif values[0] == "VV" and  values[1] == "VF" and values[2] == "FF":
                       if value == "FF" :
                           graph.add_edge(source_node, target_node)
                       if value == "VV" :
                           graph.add_edge(target_node, source_node)
                    elif values[0] == "VV" and  values[1] == "FV" and values[2] == "FF":
                       if value == "FF" :
                           graph.add_edge(target_node, source_node)
                       if value == "VV" :
                           graph.add_edge(source_node, target_node)
                    elif values[0] == "VF" and  values[1] == "FV" and values[2] == "FF":
                       if value == "VF" :
                           graph.add_edge( source_node, target_node)
                       if value == "FV" :
                           graph.add_edge(target_node, source_node)
    return graph


# Création du graphe
graph = nx.DiGraph()  # pour afficher le graphe
graph = create_graph(dico_ampoules_Vrai_faux)
# Affichage du graphe
print(graph)
def visualize_graph(nodes, edges):
    """affiche le graphe"""
    # Créer un graphe dirigé
    G = nx.DiGraph()
    # Ajouter les nœuds au graphe
    for node in nodes:
        G.add_node(node)
    # Ajouter les arêtes au graphe
    for source, destinations in edges.items():
        for destination in destinations:
            G.add_edge(source, destination)
    # Calculer le nombre de nœuds
    num_nodes = len(G.nodes())
    # Calculer le rayon du losange
    radius = math.sqrt(num_nodes)
    # Définir les positions initiales des nœuds pour former un losange
    pos = {}
    for i, node in enumerate(G.nodes()):
        angle = 2 * math.pi * (i / num_nodes)  # Angle en radians
        x = radius * math.cos(angle)
        y = radius * math.sin(angle)
        pos[node] = (x, y)
    # Dessiner le graphe
    nx.draw(G, pos, with_labels=True, node_size=1000, node_color="skyblue", font_size=12, font_weight="bold", arrowsize=20)
    plt.title('Graph Visualization')
    # Activer le mode interactif
    plt.ion()
    # Afficher le graphe
    plt.show()
    plt.pause(18)


visualize_graph(graph.nodes, graph.edges)


def positions_ampoules(data):
    """Je veux les coordonnées des ampoules"""
    positions = []
    for row in data:
        positions.append((row[0], row[1]))
    return positions


from collections import deque
def bfs_full_graph(graph):
    """Fonction principale de la partie 1, elle effectue un parcours en largeur"""
    positions_ampoules_restantes = positions_ampoules(data)
    visited_dico = {}
    visited_edges = {}
    first_node = None
    noeud_contradictoires = []
    count  = 0
    i= 0
    for node in graph.edges:
        # nombre d'arêtes comptabilisée = nombre d'ampoule. A noter que l'on ne comptabilise que seulement un sens !
        # sinon l'ampoule est comptée deux fois.
        if len(visited_edges) == len(data):

            print("Oui, on peut allumer toutes les ampoules.")
            return
        #ddata

        if len(visited_dico) == data[-1][0] *2 :  #si on est passé par autant de noeud qu'il y a d'interrupteurs, on s'arrête
            break
        queue = [node]
        while queue and count < 2  :   # Si on a une contradiction en partant d'un interrupteur dans une position fixée,
                                       # alors on peut allumer (toutes les ampoules) toutes les ampoules de la composante connexe

                                       # ssi il n'y a pas de contradiction en partant de ce même interrupteur dans l'autre position.
            if i == 1 : # on est sorti de la composante connectée
                i = 0
            current_node = queue.pop(0)
            if first_node is None :             #on marque le premier noeud visité
                first_node = current_node
            if current_node[1:3] in visited_dico:
                # Si on a une contradiction dans un sous graphe connecté, le i nous assure qu'on est toujours au sein du graphe
                #connecté
                if visited_dico[current_node[1:3]] != current_node[-2] and i == 0:
                # le cas échéant, il faut tout réinitialiser et repartir à partir du noeud opposé
                    print(f"Le nœud {current_node} contredirait la suite.")
                    # Réinitialiser la liste des nœuds visités
                    visited_dico = {}
                    queue = []
                    visited_edges= {}
                    # Explorer à partir du premier nœud opposé
                    count+=1
                    noeud_contradictoires.append(current_node)
                    # on part du noeud opposé
                    if  first_node[-2] == "V" :
                        opposite_node = f"({first_node[1:3]}, F)"
                        print(opposite_node, "opposite node ")
                        queue.append(opposite_node)
                    elif first_node[-2] == "F" :

                        opposite_node = f"({first_node[1:3]}, V)"
                        queue.append(opposite_node)
                elif visited_dico[current_node[1:3]] != current_node[-2] and i>0:  # Il s'agit d'une contradiction émanant
                                                                                   # d'une autre composante connexe
                    print(f"Le nœud {current_node} contredirait la suite. On ne considère pas les implications partant de ce noeud ")

                    break
            else:
                if current_node[1:3] not in visited_dico:
                    visited_dico[current_node[1:3]] = current_node[-2]
                if current_node in graph.edges:
                    #on parcourt les voisins
                    for neighbor in graph.edges[current_node]:
                        # on enlève les ampoules restantes
                        if current_node[1] == "Y":
                            if (int(current_node[2]), int(neighbor[2])) in positions_ampoules_restantes:
                                positions_ampoules_restantes.remove((int(current_node[2]), int(neighbor[2])))
                        if current_node[1] == "X":
                            if (int(neighbor[2]), int(current_node[2])) in positions_ampoules_restantes:
                                positions_ampoules_restantes.remove((int(neighbor[2]), int(current_node[2])))
                        #on marque l'edge visité
                        if (neighbor, current_node) not in visited_edges:
                            visited_edges[(current_node, neighbor)] = "T"
                        # on marque le sommet visité
                        if neighbor not in visited_dico:
                            queue.append(neighbor)
            if len(queue) ==0 : # on est sorti de la composante connexe
                i+=1
    print("nous sommes sortis du while")
    if len(visited_edges) == len(data):
        print(" oui on peut allumer toutes les ampoules ")  # Si on a autant d'arrêtes que d'ampoules on est bon

    else:
        #si on a une ampoule avec 3 combinaisons, il se peut qu'on a pas su relier toute les arêtes en se basant
        # uniquement sur les implications
        creer_arrete = False
        for ampoule in dico_ampoules_Vrai_faux :
            if len(dico_ampoules_Vrai_faux[ampoule])==3 :
                # Sinon on vérifie si on peut rajouter des arrêtes qu'on ne pouvait pas définir directement avec les implications
                #Cela est dû au fait que avec 3 combinaisons possible pour une m$eme ampoule, il se peut que l'on n'aie pas
                #pas directement une implication. Pour ce faire il faudra alors revérifier s'il y a moyen toutefois de
                # vérifier s'il existe en effet une combinaison pouvant créer une nouvelle arrête et donc allumer une autre
                #ampoule.

                for pos in positions_ampoules_restantes:
                    if pos in dico_ampoules_Vrai_faux :
                            valueY = visited_dico[f"Y{pos[0]}"]
                            newNodeY = f"(Y{pos[0]}, {valueY})"
                            valueX = visited_dico[f"X{pos[1]}"]
                            newNodeX = f"(X{pos[1]}, {valueX})"

                            if visited_dico[f"Y{pos[0]}"] + visited_dico[f"X{pos[1]}"] in dico_ampoules_Vrai_faux[pos]:
                                print(newNodeY, "-------->", newNodeX,
                                      " on ajoute les edges puisque les combinaisons sont propices ")
                                # on peut ajouter les edges restants
                                graph.add_edge(newNodeY, newNodeX)
                                visited_edges[(newNodeY, newNodeX)] = "T"

    if len(visited_edges) == len(data):

        print(" oui on peut allumer toutes les ampoules ")
    else:
        print(" Non on peut pas allumer toutes les ampoules malgrè qu'on a ajouté les edges restants ")

    print(noeud_contradictoires, "noeud(s) contradictoire(s)")

bfs_full_graph(graph)

def traverse_graph2(graph, noeud_contrdic):
    """Fonction principale de la partie 2 elle effectue aussi un parcours en largeur, sauf que lorsqu'un noeud contradictoire
    est atteint, on ignore l'arête menant à lui, et on continue le parcours"""
    # si on avait voulu commencer à un noeud en particulier
    start_node = "(Y1, V)"
    #queue = [start_node]
    # print(queue, "queue ")

    visited_dico = {}
    positions_ampoules_restantes = []
    visited_edges = {}
    for node in graph.edges:
        queue = [node]
        while queue:
            current_node = queue.pop(0)


            # on ne visite pas les noeuds contrdictoires
            if current_node in noeud_contrdic:
                pass
            else:
                # Si on a visité le noeud opposé
                if current_node[1:3] in visited_dico and visited_dico[current_node[1:3]] != current_node[-2]:
                    # print(visited_dico," visited dico 480 ")
                    # print(f" on ne visite pas la branche associée à {current_node} ")
                    # On va ajouter au dictionnaire noeud contradictoire le "noeud contrdictoire"
                    noeud_contrdic.append(current_node)
                    pass
                # On a pas visité le noeud ou on a pas visité son opposé
                else:
                    # si on est pas encore passé par ce noeud du tout
                    if current_node[1:3] not in visited_dico:
                        visited_dico[current_node[1:3]] = current_node[-2]

                    # on regarde les voisins du noeud current
                    if current_node in graph.edges:
                        for neighbor in graph.edges[current_node]:
                            print(neighbor, "neighbor de", current_node)
                            # ce bloc s'occupe de voir quels ampoules reste t il a allumé
                            # en discutant ce que l'arrete vient d'un X ou Y
                            if current_node[1] == "Y":
                                if (int(current_node[2]), int(neighbor[2])) in positions_ampoules_restantes:
                                    positions_ampoules_restantes.remove((int(current_node[2]), int(neighbor[2])))
                            if current_node[1] == "X":
                                if (int(neighbor[2]), int(current_node[2])) in positions_ampoules_restantes:
                                    positions_ampoules_restantes.remove((int(neighbor[2]), int(current_node[2])))

                            # print(visited_dico, "visited dico ")
                            # print(current_node, " current node ")

                            # si le edges n'a pas été créé et si le voisin peut etre atteint et si on a pas deja un edge inverse
                            if (current_node, neighbor) not in visited_edges and neighbor not in noeud_contrdic and (neighbor, current_node) not in visited_edges:
                                # si le voisin n'a jamais été visité
                                if neighbor[1:3] not in visited_dico:
                                    # print((current_node, neighbor), "(current_node, neighbor) not in visited dico   ")
                                    visited_edges[(current_node, neighbor)] = "T"
                                    visited_dico[neighbor[1:3]] = neighbor[-2]
                                    # print(visited_edges, "visieted edges ")
                                    queue.append(neighbor)
                                # si le voisin a déjà été visité
                                elif neighbor[1:3] in visited_dico:
                                    # print(neighbor, "neighbour in visited dico ")
                                    if visited_dico[neighbor[1:3]] == neighbor[-2]:
                                        # print((current_node, neighbor), "(current_node, neighbor)  ")
                                        visited_edges[(current_node, neighbor)] = "T"
                                        # print(visited_edges, "visieted edges ")
                                        queue.append(neighbor)

                            if neighbor[1:3] not in visited_dico:
                                # print(neighbor, "neighbor est ajouté a la queuee ")
                                queue.append(neighbor)
                                #visited_dico[neighbor[1:3]] = neighbor[-2]
    print(len(visited_edges), "visited edges and len de visited edges",visited_edges)
    print(visited_dico, "visited dico ")
    return len(visited_edges)


print(traverse_graph2(graph, []), "ici ")

"""Ce qui se trouve en bas est pour le backtracking"""
liste = []
class MotBool:
    """ génération exhaustives de booleans"""
    def essai(self, n):
        self.taille = n
        self.liste_combinaisons = []  # Liste pour stocker les combinaisons booléennes
        self.liste_bolean = [None] * n  # Liste pour stocker la combinaison en cours
        self.generation(0)

    def generation(self, i):
        if i == self.taille:
            self.liste_combinaisons.append(list(self.liste_bolean))  # Ajoute la combinaison à la liste globale
        else:
            self.liste_bolean[i] = True
            self.generation(i + 1)
            self.liste_bolean[i] = False
            self.generation(i + 1)


# Utilisation de la classe MotBool
motBool = MotBool()
motBool.essai((data[-1][0])*2) # Génère les combinaisons
# motBool.afficher_combinaisons()  # Remplit la liste de combinaisons
combinaisons = motBool.liste_combinaisons  # Accède à la liste de combinaisons


# print(combinaisons)

def choix_contrdictoire(liste):
    i = 0
    while i < len(liste):
        if liste[i] == True:
            del liste[i]
        else:
            i += 1
    return liste


print(choix_contrdictoire([True, True, True, True, True, True, False, False]), " cjhoix contrdicatoire ")


def get_ith_node_if_false(node_list, bool_list):
    """# Exemple d'utilisation
node_list = ['(Y1, V)', '(X2, V)', '(X4, F)', '(Y3, F)', '(X3, F)', '(X1, F)', '(Y2, F)', '(Y4, F)']
bool_list = [True, True, True, True, True, True, False, False]
i = 7  # Indice de l'élément à retourner si False
result = get_ith_node_if_false(node_list, bool_list)
print(result)  # Output: '(Y4, F)'"""
    result_node = []
    for node_str, boolean in zip(node_list, bool_list):
        if not boolean:  # Si la valeur dans la liste booléenne est False
            result_node.append(node_str)  # Assigner le nœud à la chaîne résultante
            # Arrêter la recherche dès qu'un nœud correspondant est trouvé
    return result_node


# print(get_ith_node_if_false(graph.nodes,combinaisons[7]),"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")


def parcours(graph, combinaisons):
    """Fonction qui appel traverseGraph2 et lui donne en paramètre l'exhaustivité des liste de bolean"""
    # pour toutes les listes de boleans qui nous disent si on passe par tel ou tel noeud ou pas
    i = 0
    count = 0
    traverse = traverse_graph2(graph, [])
    for bolean_liste in combinaisons:
        # on choisit le/les noeuds à ne pas traverser, i.e. les noeuds dans noeud_contrdictoires
        choix = get_ith_node_if_false(graph.nodes, bolean_liste)

        # on appel la fonction traverse avec en parametre les noeuds à ne pas trave rser
        i += 1
        print(choix, "choix numero ", i)
        count = 0

        if (traverse_graph2(graph, choix)) > traverse:
            traverse = traverse_graph2(graph, choix)
            # res = 256-count
    return traverse


print("Nombre maximal d'ampoule allumée est : " ,parcours(graph, combinaisons), )
print("Nombre maximal d'ampoule allumée est : " ,parcours(graph, combinaisons), )




