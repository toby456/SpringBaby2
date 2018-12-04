package com.qa.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.constants.BabyConstants;
import com.qa.domain.Baby;
import com.qa.repository.IBabyNameRepo;
import com.qa.webservices.IConsumeBabyName;

@Service
public class BabyServiceable implements IBabyService{
	
	@Autowired
	private IBabyNameRepo repo;
	
	@Autowired
	private IConsumeBabyName consumeBabyName;

	@Override
	public String deleteBaby(Long id) {
		if (repo.findById(id) != null) {
			repo.deleteById(id);
			return BabyConstants.BABY_DELETED;
		}
		return BabyConstants.BABY_NOT_FOUND;
	}

	@Override
	public Optional<Baby> findBaby(Long id) {
		return repo.findById(id);
	}

	@Override
	public Iterable<Baby> getAllBabies() {
		return repo.findAll();
	}

	@Override
	public String updateBaby(Long id, Baby baby) {
		if (baby.getBabyName() != BabyConstants.BATMAN || baby.getBabyName() != BabyConstants.NUTELLA
				|| baby.getBabyName() != BabyConstants.RAMBO) {
			if (repo.findById(id) != null) {
				Baby oldBaby = repo.findById(id).get();
				oldBaby.setBabyName(baby.getBabyName());
				return BabyConstants.BABY_UPDATED;
			}
			return BabyConstants.BABY_NOT_FOUND;
		}
		
		return BabyConstants.ILLEGAL_NAME;
	}

	@Override
	public String createBaby(Baby baby) {
		baby.setBabyName(consumeBabyName.getBabyName(baby));
		repo.save(baby);
		return baby.getBabyName();
	}

}
