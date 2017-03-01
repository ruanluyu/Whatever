package system;

import system.ResManager.imgBodyId;
import system.ResManager.imgBulletId;
import system.ResManager.imgFaceId;
import system.ResManager.imgMaterialId;
import system.ResManager.imgMonsterId;
import system.ResManager.imgWeaponId;

public class ImgIdMessage {
	public ImgIdMessage() {
	}

	public imgMaterialId materialId = imgMaterialId.NULL;
	public imgBulletId bulletId = imgBulletId.NULL;
	public imgBodyId bodyId = imgBodyId.NULL;
	public imgFaceId faceId = imgFaceId.NULL;
	public imgMonsterId monsterId = imgMonsterId.NULL;
	public imgWeaponId weaponId = imgWeaponId.NULL;

	public int getId() {
		int back = 0;
		if (materialId != imgMaterialId.NULL || -1 == (back += imgMaterialId.values().length - 1)) {
			back += materialId.ordinal() - 1;
		} else if (bulletId != imgBulletId.NULL || -1 == (back += imgBulletId.values().length - 1)) {
			back += bulletId.ordinal() - 1;
		} else if (bodyId != imgBodyId.NULL || -1 == (back += imgBodyId.values().length - 1)) {
			back += bodyId.ordinal() - 1;
		} else if (faceId != imgFaceId.NULL || -1 == (back += imgFaceId.values().length - 1)) {
			back += faceId.ordinal() - 1;
		} else if (monsterId != imgMonsterId.NULL || -1 == (back += imgMonsterId.values().length - 1)) {
			back += monsterId.ordinal() - 1;
		} else if (weaponId != imgWeaponId.NULL) {
			back += weaponId.ordinal() - 1;
		} else
			back = -1;
		return back;
	}
}