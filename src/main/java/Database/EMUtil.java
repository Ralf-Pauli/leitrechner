package Database;

//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//
//public class EMUtil {
//    private static EntityManagerFactory emf = null;
//    private static EntityManager em = null;
//
//    public static EntityManager holeEM(){
//        if(emf==null){
//            //                                            !!!!!!!!!!!!!!!!!!
//            emf = Persistence.createEntityManagerFactory("HierDiePUEintragen");
//        }
//        if(em==null){
//            em = emf.createEntityManager();
//        }
//        return em;
//    }
//
//    public static void close(){
//        if(em!=null){
//            em.close();
//            em=null;
//        }
//        if(emf!=null){
//            emf.close();
//            emf=null;
//        }
//    }
//
//}
