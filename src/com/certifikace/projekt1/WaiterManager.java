package com.certifikace.projekt1;

import java.util.ArrayList;
import java.util.List;

public class WaiterManager {

    private List<Waiter> waiterList;
    public WaiterManager() {this.waiterList = new ArrayList<>();}

    public void addWaiter(Waiter waiter) throws RestaurantException {
        // OŠETŔENÍ - Počet číšníků musí být třímístný - nelze přidat více číšníku nebo servírek nad počet 999
        if (waiterList.size() > 998) {
            throw new RestaurantException("Chyba - Nelze přidat číšníka. Byl dosažen maximální počet číšníků " +
                    "v pracovním poměru");
        }
        waiterList.add(waiter);
    }





}
