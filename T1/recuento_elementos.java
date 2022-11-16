public static void main(String[] args) throws FileNotFoundException, IOException {

        int a, e, i, o, u;
        a = e = i = o = u = 0;
     
        String ruta = args[0];

        try {
            FileReader fr = null;
            File f = new File(ruta);
            if (!f.exists()) {
                System.out.println("El archivo no existe");
                System.exit(1);
            }
            if (f.isDirectory()) {
                System.out.println("La ruta indicada es un directorio");
                System.exit(1);
            }
            fr = new FileReader(f);
            int c;
            while ((c = fr.read()) != -1) {
                char letra = (char) c;
                if (letra == 'a' || letra == 'A') {
                    a++;
                }
                if (letra == 'e' || letra == 'E') {
                    e++;
                }
                if (letra == 'i' || letra == 'I') {
                    i++;
                }
                if (letra == 'o' || letra == 'O') {
                    o++;
                }
                if (letra == 'u' || letra == 'U') {
                    u++;
                }
            }
            
            System.out.println("El número de aes es " + a);
            System.out.println("El número de es es " + e);
            System.out.println("El número de íes es " + i);
            System.out.println("El número de oes es " + o);
            System.out.println("El número de úes es " + u);

        } catch (IOException ex) {
			System.out.println("Error de entrada/salida");
        }
    }
