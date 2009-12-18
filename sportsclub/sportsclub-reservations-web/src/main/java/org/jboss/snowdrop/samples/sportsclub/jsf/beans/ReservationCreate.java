package org.jboss.snowdrop.samples.sportsclub.jsf.beans;

import org.jboss.snowdrop.samples.sportsclub.service.ReservationService;
import org.jboss.snowdrop.samples.sportsclub.service.EquipmentService;
import org.jboss.snowdrop.samples.sportsclub.service.AccountService;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Reservation;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Account;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Equipment;

import javax.faces.model.SelectItem;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;
import java.util.Collection;

/**
 * @author <a href="mailto:lvlcek@redhat.com">Lukas Vlcek</a>
 */
public class ReservationCreate
{
   private ReservationService reservationService;
   private EquipmentService equipmentService;
   private AccountService accountService;

   private Reservation reservation;

   public void init()
   {
      Date from;
      Date to;

      Calendar cal = Calendar.getInstance(Locale.US);
      cal.clear();
      from = cal.getTime();

      cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
      to = cal.getTime();

      reservation = new Reservation();
      reservation.setAccount(null);
      reservation.setEquipment(null);
      reservation.setFrom(from);
      reservation.setTo(to);
   }

   public SelectItem[] getAllEquipments()
   {
      Collection<Equipment> equipments =  equipmentService.getAllEquipments();
      SelectItem[] items = new SelectItem[equipments.size()];
      int i = 0;
      for (Equipment e : equipments)
      {
         String label = e.getEquipmentType().name() + ", " + e.getDescription();
         items[i++] = new SelectItem(e, label);
      }
      return items;
   }

   public SelectItem[] getAllAccounts()
   {
      Collection<Account> accounts = accountService.getAllAccounts();
      SelectItem[] items = new SelectItem[accounts.size()];
      int i = 0;
      for (Account a : accounts)
      {
         String label = a.getSubscriber().getName().getFirstName() + " " +
            a.getSubscriber().getName().getLastName() + " (" +
            a.getSubscriber().getAddress().getCity() + ", " +
            a.getSubscriber().getAddress().getCountry() + ")";
         items[i++] = new SelectItem(a, label);
      }
      return items;
   }

   public String create()
   {
      reservationService.create(reservation);
      return "success";
   }

   public ReservationService getReservationService()
   {
      return reservationService;
   }

   public void setReservationService(ReservationService reservationService)
   {
      this.reservationService = reservationService;
   }

   public Reservation getReservation()
   {
      return reservation;
   }

   public void setReservation(Reservation reservation)
   {
      this.reservation = reservation;
   }

   public EquipmentService getEquipmentService()
   {
      return equipmentService;
   }

   public void setEquipmentService(EquipmentService equipmentService)
   {
      this.equipmentService = equipmentService;
   }

   public AccountService getAccountService()
   {
      return accountService;
   }

   public void setAccountService(AccountService accountService)
   {
      this.accountService = accountService;
   }
}