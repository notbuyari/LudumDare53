package game.entity.mob.enemy;

import java.util.List;

import game.entity.Entity;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.entity.pickup.weapon.FlamethrowerPickup;
import game.entity.pickup.weapon.MachineGunPickup;
import game.entity.pickup.weapon.RocketLauncherPickup;
import game.entity.pickup.weapon.ShotgunPickup;
import game.entity.pickup.weapon.SniperRiflePickup;
import game.entity.pickup.weapon.SubmachinePickup;
import game.gfx.Art;
import game.gfx.Bitmap;
import game.task.AttackTask;
import game.task.MoveTask;
import game.task.Task;
import game.weapon.Flamethrower;
import game.weapon.MachineGun;
import game.weapon.Pistol;
import game.weapon.RocketLauncher;
import game.weapon.Shotgun;
import game.weapon.SniperRifle;
import game.weapon.Submachine;
import game.weapon.Weapon;

public class Enemy extends Mob {
	
	private static final String[] ETHNICITIES = { "caucasian", "hispanic", "black", "asian" };
	public Weapon[] weapons = { new Pistol(this), new Shotgun(this), new Submachine(this), new RocketLauncher(this), new Flamethrower(this), new SniperRifle(this), new MachineGun(this) };
	protected boolean noHair;
	protected String ethnicity;
	protected int hairCol;
	protected int skinCol;
	
	public Enemy(double x, double y) {
		super(x, y);
		maxHealth = health = random.nextInt(50, 75);
		weapon = weapons[random.nextInt(7)];
		ethnicity = ETHNICITIES[random.nextInt(4)];
		sprite = 13;
		
		
		switch(ethnicity) {
			case "caucasian": {
				hairCol = random.nextBoolean() ? 0x896B58 : 0xFFD800;
				skinCol = 0xFFBC8C;
				break;
			}
			
			case "hispanic": {
				hairCol = 0x000000;
				skinCol = 0xBC7A4B;
				break;
			}
			
			case "black": {
				hairCol = 0x000000;
				skinCol = 0x724E35;
				break;
			}
			
			case "asian": {
				hairCol = 0x000000;
				skinCol = 0xFFB200;
				break;
			}
		}
	}
	
	public Task getNextTask() {
		Task result = super.getNextTask();
		Entity target = findTarget();
		if(random.nextDouble() < 0.01) result = new MoveTask(random.nextDouble() * ((level.w << 4) - 16), random.nextDouble() * ((level.h << 4) - 16));
		else if(random.nextDouble() < 0.03 && target != null) {
			result = new AttackTask((Mob)target, 1000, weapon.maxRange / 2);
		}
		
		return result;
	}
	
	public void updateWeapon(Mob target) {
		if((weapon.maxAmmoLoaded == 0 || weapon.ammoLoaded > 0) && target != null) {
			shootAt(target);
		} else weapon.reload();
		
		if(target != null && weapon.ammoLoaded < weapon.maxAmmoLoaded && weapon.ammoLoaded <= 0) {
			weapon.reload();
		}
		
	}
	
	public void shootAt(Entity target) {
		if(shootTime > 0) return; 
		
		double lead = Math.sqrt(target.distanceToSqr(this)) * weapon.aimLead / 5;
		
		double xd = (target.x + target.xa * lead) - x;
		double yd = (target.y + target.ya * lead) - y;
		
		double dd = Math.sqrt(xd * xd + yd * yd);
		xd /= dd;
		yd /= dd;
		
		weapon.shoot(xd, yd, 0);
		rot = Math.atan2(yd, xd);
		
		shootTime = 60;
	}
	
	
	public Entity findTarget() {
		double r = weapon.maxRange;
		List<Entity> entities = level.getEntities(x - r, y - r, x + r, y + r);
		Entity closest = null;
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if(e instanceof Mob && e != this) {
				Mob u = (Mob)e;
				if(u.isAlive() && u.distanceToSqr(this) < r * r && isLegalTarget(u)) {
					if(closest == null) closest = e;
					else if(e.distanceToSqr(this) < closest.distanceToSqr(this)) {
						closest = e;
					}
				}
			}
		}
		
		return closest;
	}
	
	public void render(Bitmap screen) {
		if(hurtTime > 0 && hurtTime / 4 % 2 == 0) return;
		
		int x = (int)this.x - 8;
		int y = (int)this.y - 12;
		
		if(!weapon.isShooting()) {
			dir = (int)(-Math.floor(rot * 4 / (Math.PI * 2) - 0.5)) & 3;
			if(dir == 3) {
				dir = 1;
				screen.flipped = true;
			}
			
			int frame = (weapon.getSprite() * 16) + (dir * 3 + frames[(int)(movement / 10) % frames.length]);
			
			screen.draw(Art.recolor(Art.sprites[frame], skinCol, hairCol), x, y - (int)z);
			screen.flipped = false;			
		} else {
			dir = (int)(-Math.floor(rot * 4 / (Math.PI * 2) - 0.5)) & 3;
			
			int frame = (weapon.getSprite() * 16);
			
			switch(dir) {
				case 0: {
					frame += 9;
					break;
				}
				
				case 1: {
					frame += 10;
					break;
				}
				
				case 2: {
					frame += 11;
					break;
				}
				
				case 3: {
					frame += 10;
					screen.flipped = true;
					break;
				}
				
			}
			
			screen.draw(Art.recolor(Art.sprites[frame], skinCol, hairCol), x, y - (int)z);
			screen.flipped = false;			
		}
	}
	
	public void remove() {
		super.remove();
		
		if(weapon instanceof Shotgun) level.addEntity(new ShotgunPickup(x, y));
		if(weapon instanceof MachineGun) level.addEntity(new MachineGunPickup(x, y));
		if(weapon instanceof SniperRifle) level.addEntity(new SniperRiflePickup(x, y));
		if(weapon instanceof RocketLauncher) level.addEntity(new RocketLauncherPickup(x, y));
		if(weapon instanceof Flamethrower) level.addEntity(new FlamethrowerPickup(x, y));
		if(weapon instanceof Submachine) level.addEntity(new SubmachinePickup(x, y));
	}
	
	public boolean isLegalTarget(Entity e) { return e instanceof Player; }
	
}