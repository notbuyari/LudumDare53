package game.weapon;

import java.util.Random;

import game.entity.mob.Mob;

public abstract class Weapon {

	public static final Random random = new Random();
	public Mob owner;
	public int maxAmmoLoaded;
	public int maxAmmoCarried;
	public int ammoLoaded;
	public int ammoCarried;
	
	public double shootDelayTime;
	public double startReloadDelayTime;
	public double reloadDelayTime;
	
	public double shootDelay;
	public double reloadDelay;
	public boolean wasReloading;
	public double maxRange = 150;
	public double nearDistance = 0;
	public double midDistance = 512;
	public double farDistance = 1024;
	public double highRamp = 175;
	public double lowRamp = 50;
	public double aimLead = 2;
	
	public boolean infiniteAmmo;
	
	public Weapon(Mob owner) { this.owner = owner; }
	
	public void tick() {
		if(shootDelay > 0) shootDelay -= 1 / 60.0;
		wasReloading = reloadDelay > 0;
		if(reloadDelay > 0) reloadDelay -= 1 / 60.0;
		if(ammoCarried <= 0) ammoCarried = 0;
	}
	
	public void shoot(double xa, double ya, double za) {
		if(!infiniteAmmo) ammoLoaded--;
	}
	
	public boolean canUse() {
		return shootDelay <= 0 && reloadDelay <= 0 && ammoLoaded > 0;
	}
	
	public boolean isShooting() {
		return shootDelay == shootDelayTime;
	}
	
	public void reload() {
		if(ammoLoaded < maxAmmoLoaded && ammoCarried > 0) {
			ammoCarried -= (maxAmmoLoaded - ammoLoaded);
			ammoLoaded = maxAmmoLoaded;
			if(!wasReloading) reloadDelay = startReloadDelayTime;
			else reloadDelay = reloadDelayTime;
		}
	}
	
	public void takeAmmo(int ammo) {
		if(maxAmmoCarried <= 0) {
			ammoLoaded += ammo;
			if(ammoLoaded > maxAmmoLoaded) ammoLoaded = maxAmmoLoaded;
		} else {
			ammoCarried += ammo;
			if(ammoCarried > maxAmmoCarried) ammoCarried = maxAmmoCarried;
		}
	}
	
	public int getAmmoCapacity() {
		if(maxAmmoCarried <= 0) return maxAmmoLoaded;
		else return maxAmmoCarried;
	}
	
	public boolean canPickupAmmo() {
		if(maxAmmoCarried <= 0) return ammoLoaded < maxAmmoLoaded;
		else return ammoCarried < maxAmmoCarried;
	}
	
	public int getSprite() { return owner.sprite + 0; }
	public int getGUISprite() { return 0; }
	
}