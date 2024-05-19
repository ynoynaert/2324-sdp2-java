package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.BusinessDao;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BusinessManagerTest {

    @Mock
    private BusinessDao businessDao;

    @InjectMocks
    private BusinessManager businessManager;

    @Test
    public void get_businesses() {
        List<Business> businessList = new ArrayList<>();
        businessList.add(new Business());
        businessList.add(new Business());
        when(businessDao.getBusinesses()).thenReturn(businessList);

        businessManager.getBusinesses();
        verify(businessDao, times(1)).getBusinesses();
    }

    @Test
    public void get_business_by_id() {

        long id = 1L;
        Business business = new Business();
        when(businessDao.getBusinessById(id)).thenReturn(business);

        Business result = businessManager.getBusinessById(id);
        verify(businessDao, times(1)).getBusinessById(id);
        assertEquals(business, result);
    }

    @Test
    public void update_business() {

        Business business = new Business();
        businessManager.updateBusiness(business);
        verify(businessDao, times(1)).update(business);
    }

    @Test
    public void add_business() {

        Business business = new Business();
        businessManager.addBusiness(business);
        verify(businessDao, times(1)).insert(business);
    }
}