package com.fileshare.filepool.repository;

import com.fileshare.filepool.model.Filepool;
import com.fileshare.filepool.model.FilepoolPage;
import com.fileshare.filepool.model.FilepoolSearchCriteria;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class FilepoolCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public FilepoolCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Filepool> findAllWithFilters(FilepoolPage filepoolPage,
                                             FilepoolSearchCriteria filepoolSearchCriteria){
        CriteriaQuery<Filepool> criteriaQuery = criteriaBuilder.createQuery(Filepool.class);
        Root<Filepool> employeeRoot = criteriaQuery.from(Filepool.class);
        Predicate predicate = getPredicate(filepoolSearchCriteria, employeeRoot);
        criteriaQuery.where(predicate);
        setOrder(filepoolPage, criteriaQuery, employeeRoot);

        TypedQuery<Filepool> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(filepoolPage.getPageNumber() * filepoolPage.getPageSize());
        typedQuery.setMaxResults(filepoolPage.getPageSize());

        Pageable pageable = getPageable(filepoolPage);

        long employeesCount = getEmployeesCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, employeesCount);
    }

    private Predicate getPredicate(FilepoolSearchCriteria filepoolSearchCriteria,
                                   Root<Filepool> employeeRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(filepoolSearchCriteria.getOwnerId())){
            predicates.add(
                    criteriaBuilder.equal(employeeRoot.get("owner_id"),
                            filepoolSearchCriteria.getOwnerId()+"")
            );
        }
        if(Objects.nonNull(filepoolSearchCriteria.getPath())){
            predicates.add(
                    criteriaBuilder.like(employeeRoot.get("path"),
                            "%" + filepoolSearchCriteria.getPath() + "%")
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(FilepoolPage filepoolPage,
                          CriteriaQuery<Filepool> criteriaQuery,
                          Root<Filepool> employeeRoot) {
        if(filepoolPage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(employeeRoot.get(filepoolPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(employeeRoot.get(filepoolPage.getSortBy())));
        }
    }

    private Pageable getPageable(FilepoolPage filepoolPage) {
        Sort sort = Sort.by(filepoolPage.getSortDirection(), filepoolPage.getSortBy());
        return PageRequest.of(filepoolPage.getPageNumber(), filepoolPage.getPageSize(), sort);
    }

    private long getEmployeesCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Filepool> countRoot = countQuery.from(Filepool.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
