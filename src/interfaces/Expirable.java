package interfaces;

public interface Expirable {
    boolean isExpired();
    boolean isExpiringSoon(int days);
}
