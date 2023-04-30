package game.entity.mob;

import game.entity.Entity;
import game.entity.projectile.Bullet;
import game.gfx.Art;
import game.gfx.Bitmap;
import game.particle.FireParticle;
import game.particle.MeatParticle;
import game.particle.SplatParticle;
import game.task.IdleTask;
import game.task.Task;
import game.tools.MathUtils;
import game.weapon.Pistol;
import game.weapon.Weapon;

public class Mob extends Entity {

	public double moveSpeed = 0.1;
	public double turnSpeed = 0.3;
	public int[] frames = { 0, 1, 0, 2 };
	public int dir;
	public int tickCount;
	public int hurtTime;
	public int health = 100;
	public int maxHealth = 100;
	public int burnTime;
	public int burnInterval;
	public int shootTime = 60;
	public Weapon weapon = new Pistol(this);
	public Task task;
	
	public Mob(double x, double y) {
		super(x, y);
		xr = 2;
		yr = 4;
	}
	
	public boolean isAlive() { return health > 0; }
	
	public void tick() {		
		onGround = z <= 1;
		
		tickCount++;
		if(health <= 0) {
			health = 0;
			remove();
		}
		
		if(burnTime > 0) {
			if(burnInterval++ >= 30) {
				burnInterval = 0;
				hurt(3);
			}
			
			burnTime--;
		}
		
		if(burnTime > 0) {
			FireParticle fire = new FireParticle(x + (random.nextDouble() - 1) * 4, y + (random.nextDouble() - 0.5) * 4, z + random.nextInt(12));
			fire.xa *= 0.1;
			fire.ya *= 0.1;
			fire.za *= 0.1;
			fire.lifeTime /= 2;
			level.addParticle(fire);
		}
		
		if(hurtTime > 0) hurtTime--;
		if(shootTime > 0) shootTime--;
		if(xa * xa + ya * ya < 0.02) movement = 0;
		
		if(!removed) weapon.tick();

		if(task != null) task.tick();
		if(task == null || task.finished()) setTask(getNextTask());
		
		attemptMove();
		
		if(xa != 0 || ya != 0) movement += 1.5;
		
		xa *= friction;
		ya *= friction;
		za -= gravity;
	}
	
	public void handleExplosion(Bullet b, int dmg, double xd, double yd, double zd) {
		if(this == b.owner) dmg /= 2;
		
		hurt(dmg);
		knockBack(xd * 2, yd * 2, zd * 2);
	}
	
	public void hitBy(Bullet b, double xxa, double yya, double zza) {
		if(hurtTime > 0) return;
		
		knockBack(b.xa * 0.25, b.ya * 0.25, b.za);
		health -= b.getDamage(b.owner);
		b.applyHitEffect(this);
		
		for(int i = 0; i < 5; i++) {
			level.addParticle(new SplatParticle(x, y, z + 5));
		}
				
		hurtTime = 30;
	}
	
	public void hurt(int dmg) {
		health -= dmg;
		
		for(int i = 0; i < 5; i++) {
			level.addParticle(new SplatParticle(x, y, z + 5));
		}
		
		hurtTime = 20;
	}
		
	public void renderShadow(Bitmap screen) {
		int x = (int)this.x;
		int y = (int)this.y;
		screen.fill(x - 2 - (int)(xr * 0.9), y + 3, x + 1 + (int)(xr * 0.9), y + 3, 1);
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
			
			screen.draw(Art.sprites[frame], x, y - (int)z);
			screen.flipped = false;			
		} else {
			dir = (int)(-Math.floor(rot * 4 / (Math.PI * 2) - 0.5)) & 3;
			
			this.sprite = weapon.getSprite();
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
			
			screen.draw(Art.sprites[frame], x, y - (int)z);
			screen.flipped = false;			
		}
	}
	
	public boolean turnTowards(double angle) {
		angle = MathUtils.normalize(angle);
		rot = MathUtils.normalize(rot);
		double angleDiff = MathUtils.normalize(angle - rot);
		double near = 1.0;
		boolean wasAimed = angleDiff * angleDiff < near * near;
		if(angleDiff < -turnSpeed) angleDiff = -turnSpeed;
		if(angleDiff > +turnSpeed) angleDiff = +turnSpeed;
		rot += angleDiff;
		return wasAimed;
	}
	
	public void moveForward() {
		xa += Math.cos(rot) * moveSpeed;
		ya += Math.sin(rot) * moveSpeed;
	}
	
	public void knockBack(double xxa, double yya, double zza) {
		xa += (xxa - xa) * 0.4;
		ya += (yya - ya) * 0.4;
		za += (zza - za) * 0.4;
	}
	
	public void jump() {
		if(!onGround) return;
		za = 1;
	}
	
	public void remove() {
		super.remove();
		
		for(int i = 0; i < 15; i++) {
			level.addParticle(new MeatParticle(x, y, z + i));
		}
	}
	
	public void updateWeapon(Mob target) {}
	
	public void heal(int toHeal) {
		int maxHeal = maxHealth - health;
		if(maxHeal <= 0) return;
		if(toHeal > maxHeal) toHeal = maxHeal;
		health += toHeal;
	}
	
	public void setTask(Task task) {
		if(task == null) return;
		this.task = task;
		task.init(this);
	}
	
	public Task getNextTask() { return new IdleTask(); }
	
}