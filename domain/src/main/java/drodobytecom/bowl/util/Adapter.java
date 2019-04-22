package drodobytecom.bowl.util;

public interface Adapter<From, To> {
   To adapt(From from);
}
