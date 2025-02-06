package com.naitech.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naitech.cursomc.domain.ItemOrder;
import com.naitech.cursomc.domain.ItemOrderPK;

@Repository
public interface ItemOrderRepository extends JpaRepository<ItemOrder, ItemOrderPK> {

}
