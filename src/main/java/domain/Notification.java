package domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private Long id;
    
    @Column(name = "account_id", nullable = false)
    private Long accountId;
    
    @Column(name = "type", nullable = false)
    private String type;
    
    @Column(name = "text", nullable = false)
    private String text;
    
    @Column(name = "data", nullable = false)
    private String data;
    
    @Column(name = "`from`", nullable = false)
    private Long from;
    
    @Column(name = "received", nullable = false)
    private int received;
    
    @Column(name = "seen", nullable = false)
    private int seen;
    
    @Column(name = "notification_type_id", nullable = false)
    private int notificationTypeId;
}
