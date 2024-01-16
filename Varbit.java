// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Varbit {

	public static void unpack(Archive archive) {
		JagBuffer buf = new JagBuffer(archive.get("varbit.dat"));
		count = buf.getShort();

		if (aClass49Array824 == null)
			aClass49Array824 = new Varbit[count];

		for (int id = 0; id < count; id++) {
			if (aClass49Array824[id] == null)
				aClass49Array824[id] = new Varbit();
			aClass49Array824[id].init(id, buf);
			if (aClass49Array824[id].aBoolean829)
				Varp.aClass43Array704[aClass49Array824[id].varpId].aBoolean716 = true;
		}

		if (buf.position != buf.buffer.length)
			System.out.println("varbit load mismatch");
	}

	public void init(int j, JagBuffer buf) {
		do {
			int attribute = buf.getByte();
			if (attribute == 0)
				return;
			if (attribute == 1) {
				varpId = buf.getShort();
				anInt827 = buf.getByte();
				anInt828 = buf.getByte();
			} else if (attribute == 10)
				aString825 = buf.getString();
			else if (attribute == 2)
				aBoolean829 = true;
			else if (attribute == 3)
				anInt830 = buf.getInt();
			else if (attribute == 4)
				anInt831 = buf.getInt();
			else if (attribute == 5)
				aBoolean832 = false;
			else
				System.out.println("Error unrecognised config code: " + attribute);
		} while (true);
	}

	public Varbit() {
		aBoolean829 = false;
		anInt830 = -1;
		aBoolean832 = true;
	}

	public int anInt822;
	public static int count;
	public static Varbit aClass49Array824[];
	public String aString825;
	public int varpId;
	public int anInt827;
	public int anInt828;
	public boolean aBoolean829;
	public int anInt830;
	public int anInt831;
	public boolean aBoolean832;
}
