package repository;

import domain.Notification;

public class NotificationDaoJpa extends GenericDoaJpa<Notification> implements NotificationDao {

    public NotificationDaoJpa() {
        super(Notification.class);
    }



    @Override
    public void insert(Notification object) {
        try {
            em.getTransaction().begin();
            em.merge(object);
            super.insert(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

        }

    }
}
