#include <stdio.h>
#include <malloc.h>

struct arbol
{
    struct arbol *der,*izq,*sig;  // forma el nodo
    int frecuencia;                  //apariciones del caracter
    char bit;                    // tiene 0 o 1 
    unsigned char letra;      // el carccter (para la descompresion)
    char *codigo;                // cadena de ceros y unos con la codificacion
    char nbits;                  // me apunto el numero de bits que codifican el caracter
}arbolOriginal[256],*nuevoArbol[256],*MENOR,*SEGUNDO;

int NSIMB=0,nsimb;
FILE *f,*g;
int NBYTES=0;

//pone todos los caracteres en la tabla

int crearArbolOriginal(char *archivo)
{
    int j;
    //encera el arbol en Null
    for(j=0;j<256;++j){
        arbolOriginal[j].der=arbolOriginal[j].izq=arbolOriginal[j].sig=NULL;  
        arbolOriginal[j].frecuencia=0;
        arbolOriginal[j].letra=j;
        arbolOriginal[j].codigo=NULL;
    }
    if ((f=fopen(archivo,"rb"))!=NULL){ //abre el archivo
        while ((j=fgetc(f))!=EOF){  //obtiene cada uno de los caracteres del archivo hasta llegar al final
            ++arbolOriginal[j].frecuencia;  //asigna cada carater su frecuencia
            ++NBYTES;  //total de caracteres hay en el archivo
        }
        fclose(f);
    }else{
        return(1); //si no se abre el archivo
    }
    for(j=0;j<256;++j){
        if (arbolOriginal[j].frecuencia!=0)
            ++NSIMB; //cuantos caracteres hay sin repetidos
    }
    nsimb=NSIMB;  //asigna cuantos elemetos hay en la tabla
    return(0);
}

//copiar todos los caracteres del arbol original al arbol nuevo
void crearNuevoArbol()
{
    int j;
    for(j=0;j<256;++j){
        nuevoArbol[j]=&(arbolOriginal[j]); //copia todos los elementos de la tabla 
    }
    return;
}

//ordena la lista por frecuencia
void ordenarArbolNuevo()
{
    int menor=-1;     /* guarda indice */
    int segundo=-1;   /* guarda indice */
    int temporal;     /* guarda la frecuencia */
    int j;
    struct arbol *P;   /* nuevo nodo */

    if (nsimb==1) return; //si no hay letras en el archivo

    // buscar primer menor
    for(j=0;j<256;++j){
        if (nuevoArbol[j]==NULL) continue;
        if (nuevoArbol[j]->frecuencia==0) continue;
        if (menor==-1){  //entra la primera vez
            menor=j;  //asigna 0
            temporal=nuevoArbol[j]->frecuencia;  //guarda la frecuencia del primer elemento
        }else if (nuevoArbol[j]->frecuencia<temporal){ //
            menor=j; //asigna el valor de j, guarda la posicion del menor
            temporal=nuevoArbol[j]->frecuencia; //guarda la frecuencia del menor
        }
    }
    //al salir del for tendra el primer elemento es decir la letra que menos frecuencia tenga

    // buscar segundo menor 
    for(j=0;j<256;++j){
        if (nuevoArbol[j]==NULL) continue;  //sige al siguiente valor
        if (nuevoArbol[j]->frecuencia==0) continue; //sige al siguiente valor
        if (j==menor) continue;  //si encuentra de nuevo el mismo menor que el primero sige al siguiente valor
        if (segundo==-1){ //entra la primera vez
            segundo=j; //asigna 0
            temporal=nuevoArbol[j]->frecuencia; //guarda la frecuencia del primer elemento
        } else if (nuevoArbol[j]->frecuencia<temporal){
            segundo=j;  //asigna el valor de j, guarda la posicion del menor
            temporal=nuevoArbol[j]->frecuencia; //guarda la frecuencia del menor
        }
    }

    /* fusiona los dos primeros arboles*/
    P=(struct arbol *)malloc(sizeof(struct arbol));
    nuevoArbol[menor]->sig=P;
    nuevoArbol[segundo]->sig=P;
    P->izq=nuevoArbol[menor];  //asigna a la iz el primer arbol menor
    P->der=nuevoArbol[segundo];  //asigna a la derecha el segundo arbol menor
    P->sig=NULL;  //en el nodo raiz asigna NULL
    nuevoArbol[menor]->bit=0;  //porque esta a la izquierda cuenta como un 0
    nuevoArbol[segundo]->bit=1; //porque esta a la derecha cuenta como un 1
    P->frecuencia=nuevoArbol[menor]->frecuencia+nuevoArbol[segundo]->frecuencia;  //suma las frecuencias de los dos arboles
    nuevoArbol[menor]=NULL;
    nuevoArbol[segundo]=P;  //asigna en la posicion del segundo menor del nuevo arbol el arbol que formamos anteriormente
    --nsimb; //como ya lo pusimos en el nuevo arbol seria un elemento menos

    //sigue formando el nuevo arbol hasta quede un nodo */
    ordenarArbolNuevo();
}

/*--------------------------------
Una vez construido el arbol, puedo codificar
cada caracter. Para eso recorro desde la hoja
a la raíz, apunto 0 o 1 en una pila y luego
paso la pila a una cadena. Un 2 determina el
fin de la cadena.
--------------------------------*/
void codificar()
{
    char pila[64];
    char tope;
    int j;
    char *w;
    struct arbol *P;
    for(j=0;j<256;++j){
        if (arbolOriginal[j].frecuencia==0) continue; 
        P=(struct arbol *)(&(arbolOriginal[j])); //crea un puntero para recorrer un caracter de todos los caracteres del archivo
        tope=0;
        //recorro ese caracter para ver cuandos 1 o 0 tiene 
        while (P->sig!=NULL){
            pila[tope]=P->bit;  //lleno una pila con los bits de cada caracter
            ++tope; //sirve para ver cuandos 1 o 0 tiene  ese caracter
            P=P->sig;
        }
        arbolOriginal[j].nbits=tope; //almacena el numero de 1 o 0 tiene  ese caracter
        arbolOriginal[j].codigo=(char *)malloc((tope+1)*sizeof(char));  // cadena de ceros y unos 
        w=arbolOriginal[j].codigo; 
        --tope;
        while (tope>-1){
            *w=pila[tope];
            --tope;
            ++w;
        }
        *w=2; //significa fin de la cadena
    }
    return;
}


/*
Imprime la frecuencia de cada
caracter asi como la cadena con que se codifica*/
void debug()
{
    int j,k;
    char *w;
    int tam_comprimido=0;
    for(j=0;j<256;++j){
        if (arbolOriginal[j].frecuencia==0) continue;
        tam_comprimido+=(arbolOriginal[j].frecuencia*arbolOriginal[j].nbits);
        printf("%3d %6d ",j,arbolOriginal[j].frecuencia);
        w=arbolOriginal[j].codigo;
        while (*w!=2){
            printf("%c",48+(*w));
            ++w;
        }
        printf("\n");
    }
    printf("Numero de Caracteres sin repetirse: %d\n",NSIMB);
    printf("Caracteres Totales: %d\n",NBYTES);
    printf("TAMANO COMPRIMIDO: %d\n",tam_comprimido/8+1);
    return;
}

/*Escribe la cabecera del archivo de
destino, contiene: el numero de bytes del archivo origen,
el numero de caracteres distintos
en ese archivo y una lista de parejas
numero de caracter-cuenta de ese
caracter. Eso es suficiente para la
descompresion*/

int crearNuevoArchivo(char *destino)
{
    int j,k;
    FILE *g;

    char *p=(char *)(&NBYTES);
    if ((g=fopen(destino,"wb"))==NULL) return(1);
    for(j=0;j<4;++j){
        fputc(*p,g);
        ++p;
    }

    p=(char *)(&NSIMB);
    fputc(*p,g);

    for(j=0;j<256;++j){
        if (arbolOriginal[j].frecuencia==0) continue;
        fputc(j,g);
        p=(char *)(&(arbolOriginal[j].frecuencia));
        for(k=0;k<4;++k){
            fputc(*p,g);
            ++p;
        }
    }
    fclose(g);
    return(0);
}

/*
Una vez construido el arbol y codificado
cada caracter podemos comprimir: se toma caracter a caracter
del archivo origen y por medio de la cadena
de codificacion escribiendo
bits en un buffer de un caracter, que
cada vez que quede lleno se pasara al
archivo de destino*/

int comprimir(char *origen, char *destino)
{
    unsigned char d=0;
    int x;
    char nbit=0;
    char *p;

    if ((f=fopen(origen,"rb"))==NULL) return(1);
    if ((g=fopen(destino,"ab"))==NULL) return(2); //archivo donde vamos a descomprimir

    while ((x=fgetc(f))!=EOF){  //abre el archvio origen
        p=arbolOriginal[x].codigo;  //recorrera el arbol original pero solo el codigo 
        while (*p!=2){
            if (nbit==8){  //entra cuando se halla agrupado los bits en grupos de 8 es decir en bytes
                nbit=0;
                fputc(d,g);  //se escribe en el archivo de destino
                d=0;
            }else if (*p==1){
                d|=(1<<nbit);
            }
            ++nbit;
            ++p;
        }
    }
    fputc(d,g);
    fclose(f);
    fclose(g);
    return(0);
}

/*--------------------------------
Descomprime el archivo. El primer paso
es leer la cabecera, paso previo a la
descompresión. Recuerdo formato de
la cabecera:
NBYTES|NSIMB|(char,cuenta)*
--------------------------------*/
int descomprimir(char *origen, char *destino)
{
    char *p;
    int j,k,n,m;
    unsigned char x,nbit;
    struct arbol *P,*Q;
    
    //abrir el archivo de origen y destino
    if ((g=fopen(origen,"rb"))==NULL) return(1);
    if ((f=fopen(destino,"wb"))==NULL) return(2);

    /* leer NBYTES */
    p=(char *)(&n);
    for(j=0;j<4;++j){
        *p=(unsigned char)fgetc(g);
        ++p;
    }
    NBYTES=n;

    /* leer NSIMB */
    NSIMB=nsimb=fgetc(g);

    /* preparar las hojas */
    for(j=0;j<256;++j){
        arbolOriginal[j].frecuencia=0;
        arbolOriginal[j].izq=arbolOriginal[j].der=arbolOriginal[j].sig=NULL;
        arbolOriginal[j].letra=j;
    }
    for(j=0;j<NSIMB;++j){
        n=fgetc(g);
        p=(char *)(&m);
        for(k=0;k<4;++k){
            *p=(unsigned char)fgetc(g);
            ++p;
        }
        arbolOriginal[n].frecuencia=m;
    }

    /* construyo el árbol */
    crearNuevoArbol();
    ordenarArbolNuevo();

    /* apunto a la raíz del árbol */
    j=0;
    while (arbolOriginal[j].frecuencia==0) ++j;
    P=(struct arbol *)(&(arbolOriginal[j]));
    while (P->sig!=NULL) P=P->sig;

    /* ahora ya se puede descomprimir */
    j=0;
    x=fgetc(g);
    nbit=0;
    Q=P;
    while(j<NBYTES){
        if (Q->izq==NULL){
            fputc(Q->letra,f);
            Q=P;
            ++j;
        } else
        if (nbit==8){
            x=fgetc(g);
            nbit=0;
        } else
        {
            if (x&(1<<nbit)){
                Q=Q->der;
            }
            else
            {
                Q=Q->izq;
            }
            ++nbit;
        }
    }
    fclose(f);
    fclose(g);
    return(0);
}

main(int argc, char *argv[])
{

printf("El Archivo se abrio");
    int j;
    if (argc<2) return;
    if (*(argv[1])=='C'){ /* comprimir */
        if (argc!=4) return;
        if (crearArbolOriginal(argv[2])){
            printf("Error al abrir el archivo\n");
            return;
        }
        crearNuevoArbol();
        ordenarArbolNuevo();
        codificar();
        if (crearNuevoArchivo(argv[3])){
            printf("Error al abrir el archivo\n");
            return;
        }
        if (comprimir(argv[2],argv[3])){
            printf("Error al abrir el archivo\n");
            return;
        }
    }
    else
    if (*(argv[1])=='D'){ /* descomprimir */
        if (argc!=4) return;
        if (descomprimir(argv[2],argv[3])){
            printf("Error al abrir el archivo\n");
            return;
        }
    }
    else
    if (*(argv[1])=='I'){ /* info */
        if (argc!=3) return;
        if (crearArbolOriginal(argv[2])){
            printf("Error al abrir el archivo\n");
            return;
        }
        crearNuevoArbol();
        ordenarArbolNuevo();
        codificar();
        debug();
    }
    return;
}
