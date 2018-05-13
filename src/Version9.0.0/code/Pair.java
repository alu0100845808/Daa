package code;

public class Pair<L,R> {

	  private final L left;
	  private final R right;

	  public Pair(L left, R right) {
	    this.left = left;
	    this.right = right;
	  }
	  
	  public Pair(Pair<L,R> par) {
		  this.left = par.getLeft();
		  this.right = par.getRight();
	  }

	  public L getLeft() { return left; }
	  public R getRight() { return right; }

	  public Pair<R,L> inverse(){
		  return new Pair<R,L>(getRight(), getLeft());
	  }
	  
	  @Override
	  public int hashCode() { return left.hashCode() ^ right.hashCode(); }

	  @Override
	  public boolean equals(Object o) {
	    if (!(o instanceof Pair)) return false;
	    @SuppressWarnings("rawtypes")
		Pair pairo = (Pair) o;
	    return this.left.equals(pairo.getLeft()) &&
	           this.right.equals(pairo.getRight());
	  }

	  public String toString() {
		  return "[" + getLeft() + ", " + getRight() + "]";
	  }
}