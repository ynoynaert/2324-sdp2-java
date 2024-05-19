package domain;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BusinessControllerTest {

    @Mock
    private BusinessManager manager;

    @InjectMocks
    private BusinessController controller;

    @Test
    void get_all_businesses() {
        ObservableList<Business> businesses = FXCollections.observableArrayList();
        when(manager.getBusinesses()).thenReturn(businesses);

        ObservableList<Business> result = controller.getAllBusinesses();

        assertEquals(businesses, result);
        verify(manager).getBusinesses();
    }

    @Test
    void get_business_by_id() {
        Business business = new Business();
        when(manager.getBusinessById(1L)).thenReturn(business);

        Business result = controller.getBusinessById(1L);

        assertEquals(business, result);
        verify(manager).getBusinessById(1L);
    }

    @Test
    void update_business() {
        Business business = new Business();

        controller.updateBusiness(business);

        verify(manager).updateBusiness(business);
    }

    @Test
    void add_business() {
        Business business = new Business();
        when(manager.addBusiness(business)).thenReturn(business);

        Business result = controller.addBusiness(business);
        assertEquals(business, result);
        verify(manager).addBusiness(business);
    }
}