package be.vmm.eenvplus.sdi.api.param;

public class ViewPortParam {

	protected int width;
	protected int height;
	protected int dpi;

	public static ViewPortParam valueOf(String s) {

		String[] p = s.split(",");
		if (p.length != 3)
			throw new IllegalArgumentException();
		return new ViewPortParam(Integer.parseInt(p[0]), Integer.parseInt(p[1]),
				Integer.parseInt(p[2]));
	}

	public ViewPortParam(int width, int height, int dpi) {
		this.width = width;
		this.height = height;
		this.dpi = dpi;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getDpi() {
		return dpi;
	}

	@Override
	public String toString() {
		return width + "," + height + "," + dpi;
	}

	@Override
	public int hashCode() {
		return width ^ height ^ dpi;
	}

	@Override
	public boolean equals(Object o) {

		if (!(o instanceof ViewPortParam)) {
			return false;
		}

		ViewPortParam v = (ViewPortParam) o;

		return width == v.width && height == v.height && dpi == v.dpi;
	}
}
