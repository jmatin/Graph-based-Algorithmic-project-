import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static class Graph {
        Map<String, Boolean> nodes;
        Map<String, List<String>> edges;

        public Graph() {
            nodes = new HashMap<>();
            edges = new HashMap<>();
        }

        public void addNode(String node) {
            if (!nodes.containsKey(node)) {
                nodes.put(node, true);
            }
        }

        public void addEdge(String fromNode, String toNode) {
            if (!nodes.containsKey(fromNode) || !nodes.containsKey(toNode)) {
                throw new IllegalArgumentException("Both nodes must be added to the graph before adding an edge.");
            }
            edges.computeIfAbsent(fromNode, k -> new ArrayList<>()).add(toNode);
        }

        public void printGraph() {
            System.out.println("Nodes:");
            for (String node : nodes.keySet()) {
                System.out.println(node);
            }
            System.out.println("Edges:");
            for (Map.Entry<String, List<String>> entry : edges.entrySet()) {
                String source = entry.getKey();
                List<String> targets = entry.getValue();
                for (String target : targets) {
                    System.out.println(source + " -> " + target);
                }
            }
        }

        @Override
        public String toString() {
            return "Nodes: " + new ArrayList<>(nodes.keySet()) + ", Edges: " + edges;
        }
    }


    public static Map<String[], String[]> lireFichierAmpoules(String cheminFichier) {
        Map<String[], String[]> ampoules = new LinkedHashMap<>();
        // Simuler la lecture depuis un fichier
        String[][] data2 = {
                {"1", "2", "0", "1", "1", "0"},
                {"1", "3", "1", "0", "0", "0"},
                {"2", "1", "0", "0", "1", "1"},
                {"2", "2", "0", "1", "1", "0"},
                {"2", "4", "1", "0", "0", "1"},
                {"3", "1", "0", "1", "1", "0"},
                {"3", "3", "1", "0", "0", "1"},
                {"3", "4", "1", "0", "1", "0"},
                {"4", "1", "1", "0", "0", "1"},
                {"4", "2", "1", "1", "0", "0"},
                {"4", "3", "0", "1", "1", "0"}
        };
        String[][] data = {

                {"1", "3", "1", "1", "1", "0"},
                {"2", "1", "1", "1", "0", "1"},
                {"2", "2", "0", "1", "1", "0"},
                {"3", "1", "1", "1", "1", "0"},
                {"3", "2", "1", "1", "0", "1"},
                {"3", "3", "1", "0", "0", "1"},
                {"2", "4", "0", "1", "1", "1"},
                {"4", "3", "1", "0", "1", "1"},
                {"4", "1", "1", "1", "1", "0"},
                {"4", "4", "0", "1", "1", "1"}
        };
        for (String[] row : data) {
            String[] key = Arrays.copyOfRange(row, 0, 2);
            String[] values = Arrays.copyOfRange(row, 2, row.length);
            ampoules.put(key, values);
        }

        // Afficher les ampoules
        for (Map.Entry<String[], String[]> entry : ampoules.entrySet()) {
            String[] key = entry.getKey();
            String[] values = entry.getValue();
            System.out.print("Ampoule " + key[0] + ", Position " + key[1] + ": ");
            for (String value : values) {
                System.out.print(value + " ");
            }
            System.out.println();
        }

        return ampoules;

    }

    public static int extractNodeNumber(String node) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(node);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        } else {
            throw new IllegalArgumentException("Le noeud n'est pas au format attendu : " + node);
        }
    }

    public static Map<List<Integer>, List<String>> convertirAmpoulesVF(Map<String[], String[]> ampoules) {
        Map<List<Integer>, List<String>> ampoulesConverties = new LinkedHashMap<>();
        for (Map.Entry<String[], String[]> entry : ampoules.entrySet()) {
            String[] keyArray = entry.getKey();
            String[] valuesArray = entry.getValue();

            // Convertir les tableaux de chaînes de caractères en listes d'entiers et de chaînes de caractères
            List<Integer> key = new ArrayList<>();
            List<Integer> values = new ArrayList<>();
            for (String str : keyArray) {
                key.add(Integer.parseInt(str));
            }
            for (String str : valuesArray) {
                values.add(Integer.parseInt(str));
            }

            List<String> result = new ArrayList<>();
            if (values.get(0) == 1) {
                result.add("VV");
            }
            if (values.get(1) == 1) {
                result.add("VF");
            }
            if (values.get(2) == 1) {
                result.add("FV");
            }
            if (values.get(3) == 1) {
                result.add("FF");
            }
            ampoulesConverties.put(key, result);
        }
        System.out.printf("ampoules converties" + ampoulesConverties);
        return ampoulesConverties;
    }

    public static void main(String[] args) {
        // Créer une liste pour stocker le résultat de la fonction lireFichierAmpoules
        List<Map<String[], String[]>> ampoulesList = new ArrayList<>();
        ampoulesList.add(lireFichierAmpoules("numero.txt"));

        // Afficher le contenu de la liste
        for (Map<String[], String[]> ampoules : ampoulesList) {
            for (Map.Entry<String[], String[]> entry : ampoules.entrySet()) {
                String[] key = entry.getKey();
                String[] values = entry.getValue();
                System.out.println("Clé : " + Arrays.toString(key) + ", Valeurs : " + Arrays.toString(values));
            }

        }
        // Appeler la fonction lireFichierAmpoules avec un chemin de fichier simulé


        // Appeler la fonction convertirAmpoulesVF sur le résultat de lireFichierAmpoules
        Map<List<Integer>, List<String>> result = convertirAmpoulesVF(lireFichierAmpoules("numero.txt"));

        // Afficher le résultat
        for (Map.Entry<List<Integer>, List<String>> entry : result.entrySet()) {
            List<Integer> key = entry.getKey();
            List<String> values = entry.getValue();
            System.out.println("Clé : " + key + ", Valeurs : " + values);
        }
        // Lire le fichier d'ampoules
        String[][] data = {
                {"1", "2", "0", "1", "1", "0"},
                {"1", "3", "1", "0", "0", "0"},
                {"2", "1", "0", "0", "1", "1"},
                {"2", "2", "0", "1", "1", "0"},
                {"2", "4", "1", "0", "0", "1"},
                {"3", "1", "0", "1", "1", "0"},
                {"3", "3", "1", "0", "0", "1"},
                {"3", "4", "1", "0", "1", "0"},
                {"4", "1", "1", "0", "0", "1"},
                {"4", "2", "1", "1", "0", "0"},
                {"4", "3", "0", "1", "1", "0"}
        };

        Map<String[], String[]> ampoules = lireFichierAmpoules("numero.txt");
        List<List<String>> ampoules_restantes = positionsAmpoules(data);

        // Créer le graphe à partir du dictionnaire converti
        Graph graph = createGraph(result);

// Afficher le contenu du graphe
        graph.printGraph();
        // Créer un exemple de graphe
        bfsFullGraph(graph);
        // Assume that you have defined and initialized your graph


        // Assume that you have defined and initialized your list of nodes to avoid
        List<String> noeudcontrdictoires = new ArrayList<>();

        traverse(graph, noeudcontrdictoires);
    }


    public static Graph createGraph(Map<List<Integer>, List<String>> dico) {
        Graph graph = new Graph();
        System.out.print(dico.entrySet());
        for (Map.Entry<List<Integer>, List<String>> entry : dico.entrySet()) {
            List<Integer> key = entry.getKey();
            List<String> values = entry.getValue();
            // Ajout de l'arête entre les nœuds
            if (values.size() == 1) {
                String sourceNode = "(Y" + key.get(0) + ", " + values.get(0).charAt(0) + ")";
                String targetNode = "(X" + key.get(1) + ", " + values.get(0).charAt(1) + ")";
                // Ajout des nœuds au graphe
                graph.addNode(sourceNode);
                graph.addNode(targetNode);
                if (values.get(0).equals("VV")) {
                    graph.addEdge(sourceNode, targetNode); //
                    graph.addEdge(targetNode, sourceNode);
                } else if (values.get(0).equals("VF")) {
                    graph.addEdge(targetNode, sourceNode);
                    graph.addEdge(targetNode, sourceNode);
                } else if (values.get(0).equals("FV")) {
                    graph.addEdge(sourceNode, targetNode);
                    graph.addEdge(targetNode, sourceNode);
                } else if (values.get(0).equals("FF")) {
                    graph.addEdge(sourceNode, targetNode);
                    graph.addEdge(targetNode, sourceNode); // on inverse
                }
            } else if (values.size() == 2) {
                String sourceNode = "(Y" + key.get(0) + ", " + values.get(0).charAt(0) + ")";
                String targetNode = "(X" + key.get(1) + ", " + values.get(1).charAt(1) + ")";
                // Ajout des nœuds au graphe
                graph.addNode(sourceNode);
                graph.addNode(targetNode);
                // Ajout de l'arête entre les nœuds
                if (values.get(0).equals("VV") && values.get(1).equals("FV")) {
                    graph.addEdge(sourceNode, targetNode); // on peut pas inverser
                } else if (values.get(0).equals("VV") && values.get(1).equals("VF")) {
                    graph.addEdge(targetNode, sourceNode); //

                } else if (values.get(0).equals("VV") && values.get(1).equals("FV")) {
                    graph.addEdge(sourceNode, targetNode); // on peut pas inverser
                } else if (values.get(0).equals("VV") && values.get(1).equals("FF")) {
                    graph.addEdge(sourceNode, targetNode);
                    graph.addEdge(targetNode, sourceNode); // on inverse
                } else if (values.get(0).equals("VF") && values.get(1).equals("FV")) {
                    graph.addEdge(sourceNode, targetNode); // on inverse
                    graph.addEdge(targetNode, sourceNode);
                } else if (values.get(0).equals("VF") && values.get(1).equals("FF")) {
                    graph.addEdge(sourceNode, targetNode); // on peut pas inverser
                } else if (values.get(0).equals("FV") && values.get(1).equals("FF")) {
                    graph.addEdge(targetNode, sourceNode); // on peut pas inverser
                }
            } else if (values.size() == 3) {
                String sourceNode = "(Y" + key.get(0) + ", " + values.get(0).charAt(0) + ")";
                String targetNode = "(X" + key.get(1) + ", " + values.get(1).charAt(1) + ")";
                // Correction de l'erreur grammaticale
                // Ajout des nœuds au graphe
                graph.addNode(sourceNode);
                graph.addNode(targetNode);
                // Ajout de l'arête entre les nœuds
                if (values.get(0).equals("VV") && values.get(1).equals("VF") && values.get(2).equals("FV")) {
                    if (values.get(2).equals("FV")) {
                        graph.addEdge(sourceNode, targetNode);
                    }
                    if (values.get(1).equals("VF")) {
                        graph.addEdge(targetNode, sourceNode);
                    }
                } else if (values.get(0).equals("VV") && values.get(1).equals("VF") && values.get(2).equals("FF")) {
                    if (values.get(2).equals("FF")) {
                        graph.addEdge(sourceNode, targetNode);
                    }
                    if (values.get(0).equals("VV")) {
                        graph.addEdge(targetNode, sourceNode);
                    }
                } else if (values.get(0).equals("VV") && values.get(1).equals("FV") && values.get(2).equals("FF")) {
                    if (values.get(2).equals("FF")) {
                        graph.addEdge(targetNode, sourceNode);
                    }
                    if (values.get(0).equals("VV")) {
                        graph.addEdge(sourceNode, targetNode);
                    }
                } else if (values.get(0).equals("VF") && values.get(1).equals("FV") && values.get(2).equals("FF")) {
                    if (values.get(0).equals("VF")) {
                        graph.addEdge(sourceNode, targetNode);
                    }
                    if (values.get(1).equals("FV")) {
                        graph.addEdge(targetNode, sourceNode);
                    }
                }
            }
        }
        int edgesCount = 0;
        for (List<String> destinations : graph.edges.values()) {
            edgesCount += destinations.size();
        }
        System.out.println("Nombre d'arêtes : " + edgesCount);
        System.out.printf("nombres de edges " + graph.edges.size());
        System.out.print("\nhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
        System.out.print(graph.nodes);
        return graph;
    }

    public static List<List<String>> positionsAmpoules(String[][] data) {
        List<List<String>> positions = new ArrayList<>();
        for (String[] row : data) {
            List<String> position = new ArrayList<>();
            position.add(row[0]);
            position.add(row[1]);
            positions.add(position);
            System.out.printf("positionssssssssssssssssssssssssssss" + positions);
        }
        return positions;
    }


    // Utilisez votre liste de listes d'entiers ici


    public static void bfsFullGraph(Graph graph) {
        Map<String[], String[]> ampoules = lireFichierAmpoules("numero.txt");
        String[][] data = {

                {"1", "3", "1", "0", "0", "0"},
                {"2", "1", "0", "0", "1", "1"},
                {"2", "2", "0", "1", "1", "0"},
                {"2", "4", "1", "0", "0", "1"},
                {"3", "1", "0", "1", "1", "0"},
                {"3", "3", "1", "0", "0", "1"},
                {"3", "4", "1", "0", "1", "0"},
                {"4", "1", "1", "0", "0", "1"},
                {"4", "2", "1", "1", "0", "0"},
                {"4", "3", "0", "1", "1", "0"}
        };
        List<List<Integer>> ampoules_converties = new ArrayList<>();
        List<List<String>> ampoules_restantes = positionsAmpoules(data);
        int data_taille = data.length;
        String nombre_interupteur_string = data[data.length - 1][0];
        int nombre_interupteur = Integer.parseInt(nombre_interupteur_string);
        // Convertir chaque élément de ampoules_restantes en liste d'entiers
        for (List<String> sousListe : ampoules_restantes) {
            List<Integer> nouvelleSousListe = new ArrayList<>();
            for (String chaine : sousListe) {
                int entier = Integer.parseInt(chaine);
                nouvelleSousListe.add(entier);
            }
            ampoules_converties.add(nouvelleSousListe);
        }
        System.out.printf("ampoules_restantes", ampoules_restantes);
        Map<String, Character> visitedDico = new HashMap<>();
        Set<String> visitedEdges = new HashSet<>();
        String firstNode = null;
        List<String> noeudContradictoires = new ArrayList<>();
        int count = 0;
        int i = 0;
        for (Map.Entry<String, List<String>> entry : graph.edges.entrySet()) {
            String sourceNode = entry.getKey();
            List<String> targetNodes = entry.getValue();

            System.out.printf("source node" + sourceNode);
            System.out.print(graph.edges);
            System.out.println("Source Node: " + sourceNode);
            System.out.println("Target Nodes: " + targetNodes);

            if (visitedEdges.size() == data_taille) {
                System.out.println(visitedEdges + " visited edges");
                System.out.println("Oui, on peut allumer toutes les ampoules.");
                return;
            }
            if (visitedDico.size() == nombre_interupteur) {  // Si on a visité les 8 noeuds on peut s'arréter ! On va alors voir si on peut
                // créer d'autre aretes
                break;
            }
            Deque<String> queue = new ArrayDeque<>();
            queue.add(entry.getKey());
            System.out.printf("queue", queue);
            while (!queue.isEmpty() && count < 2) {         // Si on a 1 contradiction en partant respctivement d'un noeud
                // en position fermée et puis allumée
                String currentNode = queue.poll();
                if (firstNode == null) {
                    firstNode = currentNode;
                }
                if (visitedDico.containsKey(currentNode.substring(1, 3))) {
                    if (visitedDico.get(currentNode.substring(1, 3)) != currentNode.charAt(currentNode.length() - 2) && i == 0) {
                        System.out.println(" on doit réinitaliser la liste des noud etccccc");
                        System.out.print(visitedDico);
                        System.out.println("Le nœud " + currentNode + " contredirait la suite.");
                        visitedDico.clear();
                        queue.clear();
                        visitedEdges.clear();
                        count++;
                        noeudContradictoires.add(currentNode);
                        if (firstNode.charAt(firstNode.length() - 2) == 'V') {
                            String oppositeNode = "(Y" + firstNode.charAt(2) + ", F)";
                            queue.add(oppositeNode);
                        } else if (firstNode.charAt(firstNode.length() - 2) == 'F') {
                            String oppositeNode = "(Y" + firstNode.charAt(2) + ", V)";
                            queue.add(oppositeNode);
                        }
                    } else if (visitedDico.get(currentNode.substring(1, 3)) != currentNode.charAt(currentNode.length() - 2) && i > 0) {
                        System.out.print(visitedDico);
                        System.out.println("Le nœud " + currentNode + " contredirait la suite. On ne considère pas les implications partant de ce noeud ");
                        continue;
                    }
                } else {
                    if (!visitedDico.containsKey(currentNode.substring(1, 3))) {
                        System.out.print(currentNode.substring(1, 3));
                        visitedDico.put(currentNode.substring(1, 3), currentNode.charAt(currentNode.length() - 2));
                    }
                    if (graph.edges.containsKey(currentNode)) {
                        System.out.print(graph.edges);
                        System.out.print("graphhhhhhhhhhhhhhhh");
                        for (String neighbor : graph.edges.get(currentNode)) {
                            System.out.printf(neighbor.charAt(2) + "ttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt");
                            System.out.printf(neighbor.charAt(2) + "ttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt");
                            System.out.printf("Ampoule_restante", ampoules_restantes);
                            // System.out.println(currentNode + " current node est dans graphe.edges");
                            //System.out.println(neighbor + " neighbor de " + currentNode);
                            //System.out.printf("currentNode[2]" + currentNode[2] );

                            if (currentNode.charAt(1) == 'Y') {

// Extrait les caractères correspondant aux chiffres
                                char neighborDigit = neighbor.charAt(2);
                                char currentNodeDigit = currentNode.charAt(2);

// Convertit les caractères en entiers
                                int neighborNumber = Character.getNumericValue(neighborDigit);
                                int currentNodeNumber = Character.getNumericValue(currentNodeDigit);

// Crée une nouvelle liste contenant les deux entiers
                                List<Integer> myList = new ArrayList<>();

                                myList.add(currentNodeNumber);
                                myList.add(neighborNumber);
                                System.out.println(myList + "mylist");
                                if (ampoules_restantes.contains(myList)) {
                                    ampoules_restantes.remove(myList);
                                }
                            }
                            if (currentNode.charAt(1) == 'X') {
                                char neighborDigit = neighbor.charAt(2);
                                char currentNodeDigit = currentNode.charAt(2);

// Convertit les caractères en entiers
                                int neighborNumber = Character.getNumericValue(neighborDigit);
                                int currentNodeNumber = Character.getNumericValue(currentNodeDigit);

// Crée une nouvelle liste contenant les deux entiers
                                List<Integer> myList = new ArrayList<>();

                                myList.add(neighborNumber);
                                myList.add(currentNodeNumber);
                                if (ampoules_restantes.contains(myList)) {
                                    ampoules_restantes.remove(myList);
                                }


                                System.out.print(visitedEdges);
                                System.out.printf(visitedEdges + "visitedyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyc edges");
                                System.out.printf(visitedEdges + "visitedyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyc edges");
                                System.out.printf(visitedEdges + "visitedyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyc edges");
                                System.out.printf(visitedEdges + "visitedyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyc edges");
                                if (!visitedEdges.contains("(" + currentNode + ", " + neighbor + ")")) {
                                    if (!visitedEdges.contains("(" + neighbor + ", " + currentNode + ")")) {
                                        visitedEdges.add("(" + neighbor + ", " + currentNode + ")");
                                    }

                                }
                                if (!visitedDico.containsKey(neighbor.substring(1, 3))) {
                                    queue.add(neighbor);
                                }
                            }
                        }
                    }
                }
                if (queue.isEmpty()) {
                    i += 1;
                }
            }

            System.out.println(ampoules_restantes.get(0) + "positions ampoules restantes2444");
            System.out.println("on est sorti du while  et du for et ça a marché heheheheh ");
            System.out.print(visitedEdges);
            System.out.print("visitedEdges");
            System.out.printf(ampoules_restantes.get(0) + "pos get ");
// Vérifier si toutes les arêtes ont été visitées
            if (visitedEdges.size() == data.length) {
                System.out.println("Oui, on peut allumer toutes les ampoules.");
            } else {
                for (List<Integer> ampoule : ampoules_converties) {
                    if (ampoule.size() == 3) {
                        for (List<String> pos : ampoules_restantes) {

                            String yKey = "Y" + pos.get(0);
                            String xKey = "X" + pos.get(1);

                            if (visitedDico.containsKey(yKey) && visitedDico.containsKey(xKey)) {
                                Character charValueY = visitedDico.get(yKey);
                                Character charValueX = visitedDico.get(xKey);
                                String valueY = String.valueOf(charValueY);
                                String valueX = String.valueOf(charValueX);
                                if (valueY != null && valueX != null) {
                                    int intValueY = Integer.parseInt(valueY);
                                    int intValueX = Integer.parseInt(valueX);

                                    String newNodeY = "(" + pos.get(0) + ", " + intValueY + ")";
                                    String newNodeX = "(" + pos.get(1) + ", " + intValueX + ")";

                                    if ((valueY + valueX).equals(ampoule.toString())) {
                                        System.out.println(newNodeY + "-------->" + newNodeX + " on ajoute les edges puisque les combinaisons sont propices ");
                                        visitedEdges.add("(" + newNodeY + ", " + newNodeX + ")");
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (visitedEdges.size() == data.length) {
                System.out.println(visitedEdges + " visited edges");
                System.out.println("Oui, on peut allumer toutes les ampoules.");
            } else {
                System.out.println("Non, on ne peut pas allumer toutes les ampoules malgré qu'on a ajouté les edges restants.");
                System.out.printf("visited edeges" + visitedEdges);
            }

            System.out.println(noeudContradictoires + " noeud contradictoires");
        }
    }


    public static List<String> traverse(Graph graph, List<String> noeudcontrdictoires) {
        Map<String, String> visitedDico = new HashMap<>();
        List<String> positionsAmpoulesRestantes = new ArrayList<>();
        Set<String> visitedEdges = new HashSet<>();

        for (Map.Entry<String, List<String>> nodeEntry : graph.edges.entrySet()) {
            String startNode = nodeEntry.getKey();
            Deque<String> queue = new ArrayDeque<>();
            queue.offer(startNode);

            while (!queue.isEmpty()) {
                String currentNode = queue.poll();
                System.out.printf("noeud contridc " + noeudcontrdictoires);
                if (noeudcontrdictoires.contains(currentNode)) {
                    continue;
                }
                // Check if the opposite node has been visited
                else {
                    if (currentNode.substring(1, 3).equals(visitedDico.get(currentNode.substring(1, 3)))
                            && !visitedDico.get(currentNode.substring(1, 3)).equals(String.valueOf(currentNode.charAt(currentNode.length() - 1)))) {
                        noeudcontrdictoires.add(currentNode);
                        ; // Skip processing this node if its opposite has been visited and has a different value
                    }
                     else
                     {
                         if (!visitedDico.containsKey(currentNode.substring(1, 3))) {

                        visitedDico.put(currentNode.substring(1, 3), String.valueOf(currentNode.charAt(currentNode.length() - 1)));}
                        System.out.printf("eeeee" + currentNode.substring(1, 3) + "nbnbnbbn");
                    }

                        if (graph.edges.containsKey(currentNode)) {
                        for (String neighbor : graph.edges.get(currentNode)) {
                            if (currentNode.charAt(1) == 'Y') {
                                if (positionsAmpoulesRestantes.contains(currentNode.charAt(2) + "," + neighbor.charAt(2))) {
                                    positionsAmpoulesRestantes.remove(currentNode.charAt(2) + "," + neighbor.charAt(2));
                                }
                            } else if (currentNode.charAt(1) == 'X') {
                                if (positionsAmpoulesRestantes.contains(neighbor.charAt(2) + "," + currentNode.charAt(2))) {
                                    positionsAmpoulesRestantes.remove(neighbor.charAt(2) + "," + currentNode.charAt(2));
                                }
                            }

                            if (!visitedEdges.contains("(" + currentNode + "," + neighbor + ")")
                                    && !noeudcontrdictoires.contains(neighbor)) {
                                visitedEdges.add("(" + (currentNode + "," + neighbor) + ")");
                                visitedDico.put(neighbor.substring(1, 3), String.valueOf(neighbor.charAt(neighbor.length() - 1)));
                                queue.offer(neighbor);
                            } else if (visitedDico.containsKey(neighbor.substring(1, 3))) {
                                if (visitedDico.get(neighbor.substring(1, 3)).equals(String.valueOf(neighbor.charAt(neighbor.length() - 1)))) {
                                    visitedEdges.add(("(" + currentNode + "," + neighbor) + ")");
                                    queue.offer(neighbor);
                                }
                            }
                            else {
                                queue.offer(neighbor);
                            }
                        }
                    }
                }
            }}

            System.out.println("Vivvvvvvvvvvvvvvvvvvvv " + visitedEdges.size() + ", Visited Dico: " + visitedDico);
    return noeudcontrdictoires;
        }}
