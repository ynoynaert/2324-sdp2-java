package domain;


import dtos.SubOrderInformationDTO;
import dtos.SubOrderItemsDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.SubOrderDao;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class SubOrderManagerTest {

    @Mock
    private SubOrderDao subOrderRepo;

    @InjectMocks
    private SubOrderManager subOrderManager;

    @Test
    void get_total_price() {
        String uuid = "qwertzuop2347820398wuzqtss";
        double expectedPrice = 100.0;
        when(subOrderRepo.getTotpriceSuborder(uuid)).thenReturn(expectedPrice);

        double actualPrice = subOrderManager.getTotalPrice(uuid);

        assertEquals(expectedPrice, actualPrice/1.21, 0.1);
        verify(subOrderRepo).getTotpriceSuborder(uuid);
    }

    @Test
    void get_sub_orders() {
        Long supplierId = 1L;
        List<SubOrder> subOrdersList = Arrays.asList(new SubOrder(), new SubOrder());
        when(subOrderRepo.getSubOrdersBySupplier(supplierId)).thenReturn(subOrdersList);

        ObservableList<SubOrder> result = subOrderManager.getSubOrders(supplierId);

        assertEquals(FXCollections.observableArrayList(subOrdersList), result);
        verify(subOrderRepo).getSubOrdersBySupplier(supplierId);
    }

    @Test
    void get_sub_orders_by_client() {
        Long supplierId = 1L;
        Long clientId = 2L;
        List<SubOrder> subOrdersSet = new ArrayList<>(Arrays.asList(new SubOrder(), new SubOrder()));
        when(subOrderRepo.getSubOrdersBySupplierAndClient(supplierId, clientId)).thenReturn(subOrdersSet);

        Set<SubOrder> result = subOrderManager.getSubOrdersByClient(supplierId, clientId);
        assertEquals(new HashSet<>(subOrdersSet), new HashSet<>(result));
        verify(subOrderRepo).getSubOrdersBySupplierAndClient(supplierId, clientId);
    }

    @Test
    void get_sub_order() {
        String uuid = "qwertzuiop1231224";
        SubOrder expectedSubOrder = new SubOrder();
        when(subOrderRepo.getSubOrderByUuid(uuid)).thenReturn(expectedSubOrder);

        SubOrder actualSubOrder = subOrderManager.getSubOrder(uuid);

        assertEquals(expectedSubOrder, actualSubOrder);
        verify(subOrderRepo).getSubOrderByUuid(uuid);
    }

    @Test
    void get_items_by_order_id() {
        int id = 1;
        List<SubOrderItemsDTO> itemsList = List.of(new SubOrderItemsDTO("Fairy Odd Parents", 12, true, 12, 144.0));
        when(subOrderRepo.getItemsBySuborderId(id)).thenReturn(itemsList);

        List<SubOrderItemsDTO> result = subOrderManager.getItemsByOrderId(id);

        assertEquals(itemsList, result);
        verify(subOrderRepo).getItemsBySuborderId(id);
    }

    @Test
    void get_sub_order_information() {
        int id = 1;
        SubOrderInformationDTO infoDTO = new SubOrderInformationDTO("", "", "", "", LocalDate.now(), "", "", "", "", "", 1, LocalDate.now().plusDays(30));
        when(subOrderRepo.getSubOrderInformation(id)).thenReturn(infoDTO);

        SubOrderInformationDTO result = subOrderManager.getSubOrderInformation(id);

        assertEquals(infoDTO, result);
        verify(subOrderRepo).getSubOrderInformation(id);
    }

    @Test
    void update_sub_order() {
        SubOrder suborder = new SubOrder();

        subOrderManager.updateSubOrder(suborder);

        verify(subOrderRepo).update(suborder);

    }
}