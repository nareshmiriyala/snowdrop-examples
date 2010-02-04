package org.jboss.snowdrop.samples.sportsclub.dao.jpa;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Reservation;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.ReservationRepository;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.ReservationSearchCriteria;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * @author Marius Bogoevici
 */
@Repository
public class JpaReservationRepository extends JpaRepository<Reservation, Long> implements ReservationRepository
{

   public JpaReservationRepository()
   {
      super(Reservation.class);
   }

   public Long countByCriteria(ReservationSearchCriteria criteria)
   {
      Query query = getQuery(criteria, "SELECT count(r.id) ");
      return (Long)query.getSingleResult();
   }

   public List<Reservation> getByCriteria(ReservationSearchCriteria criteria)
   {
      Query query = getQuery(criteria, null);
      List<Reservation> list = query.getResultList();
      if (criteria.getRange() != null)
      {
         int max = (criteria.getRange().getMaxIndex() > list.size() ? list.size() : criteria.getRange().getMaxIndex());
         list = list.subList(criteria.getRange().getMinIndex(), max);
      }
      return list;
   }

   private Query getQuery(ReservationSearchCriteria criteria, String select)
   {
      String q = (select != null ? select : "");

      q += "FROM " + Reservation.class.getSimpleName() + " r WHERE 1 = 1";

      if (criteria.getFromDate() != null)
      {
         q += " AND r.from >= :from";
      }
      if (criteria.getToDate() != null)
      {
         q += " AND r.to <= :to";
      }
      if (criteria.getEquipmentType() != null && !criteria.getEquipmentType().isEmpty())
      {
         q += " AND r.equipment.equipmentType IN (:equipmentTypes)";
      }

      Query query = entityManager.createQuery(q);

      if (criteria.getFromDate() != null)
      {
         query.setParameter("from", criteria.getFromDate());
      }
      if (criteria.getToDate() != null)
      {
         query.setParameter("to", criteria.getToDate());
      }
      if (criteria.getEquipmentType() != null && !criteria.getEquipmentType().isEmpty())
      {
         query.setParameter("equipmentTypes", criteria.getEquipmentType());
      }
      return query;
   }
}
