import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

@SuppressWarnings("serial")
public class tttfinal extends Applet implements MouseListener
{
	int xpos;
	int ypos;
	//user: 0 ; cpu: 1;
	int turn=0;
	public int grid[][][]=new int[4][4][4];
	int p;
	int tup;

	boolean mouseEntered = false;
	ttt3d table;
	
	void setMove(int i[]) {
		if(grid[i[0]][i[1]][i[2]]==0) {
			grid[i[0]][i[1]][i[2]]=1;
		}
		else {
			i=test.returnMove(grid);
			grid[i[0]][i[1]][i[2]]=1;
		}
		p=checkwin.check(grid);
		Graphics g = getGraphics();
		if(p==1)
			tup=2;
		else if(p==3)
			tup=3;
		paint(g);
		if(tup==1)
			addMouseListener(this);
	}


	public void init()
	{
		addMouseListener(this);
		int i,j,k;
		for(i=0;i<4;i++) {
			for(j=0;j<4;j++) {
				for(k=0;k<4;k++) {
					grid[i][j][k]=0;
				}
			}
		}
		tup=1;
	}

	public void paint(Graphics g)
	{
		// Drawing the initial structure
		int i,j,k,m;
		g.setColor(Color.yellow);
		g.drawLine(100,550,100,175);
		g.drawLine(600,450,600,75);

		g.setColor(Color.blue);
		int[] a={100,200,225,125,100};
		int[] b={550,550,525,525,550};

		for(i=0;i<4;i++) {
			for(j=0;j<4;j++) {
				for(k=0;k<4;k++) {
					g.drawPolygon(a,b,5);
					for(m=0;m<5;m++)
					a[m]+=100;
				}
				for(m=0;m<5;m++) {
					a[m]-=375;
					b[m]-=25;
				}
			}
			for(m=0;m<5;m++) {
				a[m]-=100;
				b[m]-=25;
			}
		}
		
		int xcod,ycod;
		xcod=225;
		ycod=78;
		for(i=0;i<4;i++) {
			for(j=0;j<4;j++) {
				for(k=0;k<4;k++) {
					if(grid[i][j][k]==1) {
						g.setColor(Color.red);
						g.fillOval(xcod,ycod,20,20);
					}
					else if(grid[i][j][k]==2) {
						g.setColor(Color.black);
						g.fillOval(xcod,ycod,20,20);
					}
					else {	}
					xcod+=100;
				}
				xcod-=425;
				ycod+=25;
			}
			xcod+=100;
			ycod+=25;
		}

		Font font = new Font("Arial",Font.BOLD,30);
		g.setFont(font);

		if(tup==2) {
			g.drawString("Computer Wins!!",700,200);
		}
		else if(tup==3) {
			g.drawString("Draw!!",700,200);
		}
		else if(tup==4)	{
			g.drawString("You Win!!",700,200);
		}
	}

	public void mouseClicked (MouseEvent me)
	{
		xpos = me.getX();
	  	ypos = me.getY();

		Graphics g = getGraphics();
	  	if(mouseEntered == true)
		{
			int[] a={100,200,225,125,100};
			int[] b={550,550,525,525,550};

			for(int i=0;i<4;i++)
			{
				for(int j=0;j<4;j++)
				{
					for(int k=0;k<4;k++)
					{
						if(xpos>a[3] && xpos<a[1] && ypos>b[2] && ypos<b[0])
						{
							//float t1=(a[0]+a[1])/2;
							//float t2=(b[0]+b[3])/2-8;
							//g.setColor(Color.red);
							//g.fillOval((int)t1,(int)t2,20,20);

							if(grid[3-i][3-j][k]==0)
							{
								grid[3-i][3-j][k]=2;
								p=checkwin.check(grid);
								if(p==2)
									tup=4;
								else if(p==3)
									tup=3;
								System.out.println((3-i)+" "+(3-j)+" "+k);
								paint(g);
								removeMouseListener(this);
								if(tup==1) {
									table=new ttt3d(checkwin.copy(grid));
									setMove(table.decision());
								}
							}
						}
						for(int m=0;m<5;m++)
						a[m]+=100;
					}
					for(int m=0;m<5;m++) {
						a[m]-=375;
						b[m]-=25;
					}
				}
				for(int m=0;m<5;m++) {
					a[m]-=100;
					b[m]-=25;
				}
			}
		}
	}

	public void mousePressed (MouseEvent me) {}
	public void mouseReleased (MouseEvent me) {}
	public void mouseEntered (MouseEvent me)
	{
		mouseEntered = true;
	}
	public void mouseExited (MouseEvent me)
	{
		mouseEntered = false;
	}
}

// Backend Code starts here

//This class is used t
class checkwin
{
	static int[][][] copy(int a[][][])
	{
		int[][][] b=new int[4][4][4];
		for(int i=0;i<4;i++)
		for(int j=0;j<4;j++)
		for(int k=0;k<4;k++)
		b[i][j][k]=a[i][j][k];
		return b;
	}
	static int[] checkMove(int a[][][])
	{
		int wins[][][]=returnWins();
		int b[]=new int[2];
		b[0]=b[1]=0;
		int min=0,max=0;
		int cnt31,cnt21,cnt11,cnt1,cnt2,cnt32,cnt22,cnt12,temp;
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				for(int k=0;k<4;k++) {
					if(a[i][j][k]==0) {
						cnt31=cnt32=0;
						cnt21=cnt22=0;
						cnt11=cnt12=0;
						for(int l=0;l<76;l++) {
							for(int m=0;m<4;m++) {
								if((wins[l][m][0]==i)&&(wins[l][m][1]==j)&&(wins[l][m][2]==k)) {
									cnt1=cnt2=0;
									q:for(int n=0;n<4;n++) {
										temp=a[wins[l][n][0]][wins[l][n][1]][wins[l][n][2]];
										if(temp==1) {
											for(int p=0;p<4;p++) {
												temp=a[wins[l][p][0]][wins[l][p][1]][wins[l][p][2]];
												if(temp==2)
													break q;
											}
											cnt1++;
										}
										else if(temp==2) {
											for(int p=0;p<4;p++) {
												temp=a[wins[l][p][0]][wins[l][p][1]][wins[l][p][2]];
												if(temp==1)
													break q;
											}
											cnt2++;
										}
									}
									if(cnt1==1)
										cnt11++;
									else if(cnt1==2)
										cnt21++;
									else if(cnt1==3)
										cnt31++;

									if(cnt2==1)
										cnt12++;
									else if(cnt2==2)
										cnt22++;
									else if(cnt2==3)
										cnt32++;
								}
							}
						}
						min=9999*cnt31+99*cnt21+9*cnt11;
						max=10000*cnt32+100*cnt22+10*cnt12;
						if(b[0]<min)
							b[0]=min;
						if(b[1]<max)
							b[1]=max;
					}
				}
			}
		}
		return b;
	}
	static int[][][] returnWins()
	{
		int win1[][][] = {
		//row-wise wins in same plane - 16 wins (4 in each plane)
		{{0,0,0},{0,0,1},{0,0,2},{0,0,3}},
		{{0,1,0},{0,1,1},{0,1,2},{0,1,3}},
		{{0,2,0},{0,2,1},{0,2,2},{0,2,3}},
		{{0,3,0},{0,3,1},{0,3,2},{0,3,3}},
		{{1,0,0},{1,0,1},{1,0,2},{1,0,3}},
		{{1,1,0},{1,1,1},{1,1,2},{1,1,3}},
		{{1,2,0},{1,2,1},{1,2,2},{1,2,3}},
		{{1,3,0},{1,3,1},{1,3,2},{1,3,3}},
		{{2,0,0},{2,0,1},{2,0,2},{2,0,3}},
		{{2,1,0},{2,1,1},{2,1,2},{2,1,3}},
		{{2,2,0},{2,2,1},{2,2,2},{2,2,3}},
		{{2,3,0},{2,3,1},{2,3,2},{2,3,3}},
		{{3,0,0},{3,0,1},{3,0,2},{3,0,3}},
		{{3,1,0},{3,1,1},{3,1,2},{3,1,3}},
		{{3,2,0},{3,2,1},{3,2,2},{3,2,3}},
		{{3,3,0},{3,3,1},{3,3,2},{3,3,3}},
		//column-wise wins in same plane - 16 wins (4 in each plane)
		{{0,0,0},{0,1,0},{0,2,0},{0,3,0}},
		{{0,0,1},{0,1,1},{0,2,1},{0,3,1}},
		{{0,0,2},{0,1,2},{0,2,2},{0,3,2}},
		{{0,0,3},{0,1,3},{0,2,3},{0,3,3}},
		{{1,0,0},{1,1,0},{1,2,0},{1,3,0}},
		{{1,0,1},{1,1,1},{1,2,1},{1,3,1}},
		{{1,0,2},{1,1,2},{1,2,2},{1,3,2}},
		{{1,0,3},{1,1,3},{1,2,3},{1,3,3}},
		{{2,0,0},{2,1,0},{2,2,0},{2,3,0}},
		{{2,0,1},{2,1,1},{2,2,1},{2,3,1}},
		{{2,0,2},{2,1,2},{2,2,2},{2,3,2}},
		{{2,0,3},{2,1,3},{2,2,3},{2,3,3}},
		{{3,0,0},{3,1,0},{3,2,0},{3,3,0}},
		{{3,0,1},{3,1,1},{3,2,1},{3,3,1}},
		{{3,0,2},{3,1,2},{3,2,2},{3,3,2}},
		{{3,0,3},{3,1,3},{3,2,3},{3,3,3}},
		//vertical wins across 4 planes - 16 wins
		{{0,0,0},{1,0,0},{2,0,0},{3,0,0}},
		{{0,0,1},{1,0,1},{2,0,1},{3,0,1}},
		{{0,0,2},{1,0,2},{2,0,2},{3,0,2}},
		{{0,0,3},{1,0,3},{2,0,3},{3,0,3}},
		{{0,1,0},{1,1,0},{2,1,0},{3,1,0}},
		{{0,1,1},{1,1,1},{2,1,1},{3,1,1}},
		{{0,1,2},{1,1,2},{2,1,2},{3,1,2}},
		{{0,1,3},{1,1,3},{2,1,3},{3,1,3}},
		{{0,2,0},{1,2,0},{2,2,0},{3,2,0}},
		{{0,2,1},{1,2,1},{2,2,1},{3,2,1}},
		{{0,2,2},{1,2,2},{2,2,2},{3,2,2}},
		{{0,2,3},{1,2,3},{2,2,3},{3,2,3}},
		{{0,3,0},{1,3,0},{2,3,0},{3,3,0}},
		{{0,3,1},{1,3,1},{2,3,1},{3,3,1}},
		{{0,3,2},{1,3,2},{2,3,2},{3,3,2}},
		{{0,3,3},{1,3,3},{2,3,3},{3,3,3}},
		//body diagonal wins - 4 wins
		{{0,0,0},{1,1,1},{2,2,2},{3,3,3}},
		{{0,0,3},{1,1,2},{2,2,1},{3,3,0}},
		{{0,3,0},{1,2,1},{2,1,2},{3,0,3}},
		{{0,3,3},{1,2,2},{2,1,1},{3,0,0}},
		//diagonal wins in same planes - 8 wins (2 in each plane)
		{{0,0,0},{0,1,1},{0,2,2},{0,3,3}},
		{{0,0,3},{0,1,2},{0,2,1},{0,3,0}},
		{{1,0,0},{1,1,1},{1,2,2},{1,3,3}},
		{{1,0,3},{1,1,2},{1,2,1},{1,3,0}},
		{{2,0,0},{2,1,1},{2,2,2},{2,3,3}},
		{{2,0,3},{2,1,2},{2,2,1},{2,3,0}},
		{{3,0,0},{3,1,1},{3,2,2},{3,3,3}},
		{{3,0,3},{3,1,2},{3,2,1},{3,3,0}},
		//diagonal wins across same vertical plane - 16 wins (4 from each side)
		{{0,0,0},{1,0,1},{2,0,2},{3,0,3}},
		{{0,0,3},{1,0,2},{2,0,1},{3,0,0}},
		{{0,1,0},{1,1,1},{2,1,2},{3,1,3}},
		{{0,1,3},{1,1,2},{2,1,1},{3,1,0}},
		{{0,2,0},{1,2,1},{2,2,2},{3,2,3}},
		{{0,2,3},{1,2,2},{2,2,1},{3,2,0}},
		{{0,3,0},{1,3,1},{2,3,2},{3,3,3}},
		{{0,3,3},{1,3,2},{2,3,1},{3,3,0}},
		{{0,0,0},{1,1,0},{2,2,0},{3,3,0}},
		{{0,3,0},{1,2,0},{2,1,0},{3,0,0}},
		{{0,0,1},{1,1,1},{2,2,1},{3,3,1}},
		{{0,3,1},{1,2,1},{2,1,1},{3,0,1}},
		{{0,0,2},{1,1,2},{2,2,2},{3,3,2}},
		{{0,3,2},{1,2,2},{2,1,2},{3,0,2}},
		{{0,0,3},{1,1,3},{2,2,3},{3,3,3}},
		{{0,3,3},{1,2,3},{2,1,3},{3,0,3}}};
		return win1;
	}

	static int check(int tab[][][])
	{
		for(int a=1;a<3;a++) {
			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					if(tab[i][j][0]==a) {
						for(int k=1;k<4;k++) {
							if(tab[i][j][k]!=a)
								break;
							if(k==3)
								return a;
						}//------16wins------------
					}
					if(tab[i][0][j]==a) {
						for(int k=1;k<4;k++)
						{
							if(tab[i][k][j]!=a)
								break;
							if(k==3)
								return a;
						}//------16wins------------
					}
					if(tab[0][i][j]==a) {
						for(int k=1;k<4;k++)
						{
							if(tab[k][i][j]!=a)
							break;
							if(k==3)
							return a;
						}//------16wins------------
					}
				}
			}
			//---------------48wins-----------------------------------
			
			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					if(tab[j][i][j]!=a)
						break;
					if(j==3)
						return a;
				}//-----4wins------------------------
				for(int j=0;j<4;j++) {
					if(tab[j][j][i]!=a)
						break;
					if(j==3)
						return a;
				}//-----4wins------------------------
				for(int j=0;j<4;j++) {
					if(tab[j][i][3-j]!=a)
						break;
					if(j==3)
						return a;
				}//-----4wins------------------------
				for(int j=0;j<4;j++) {
					if(tab[j][3-j][i]!=a)
					break;
					if(j==3)
					return a;
				}//-----4wins------------------------
			}
			//---------------16wins-----------------------------------
			if((tab[1][1][1]==a)&&(tab[0][0][0]==tab[1][1][1])&&(tab[2][2][2]==tab[3][3][3])&&(tab[1][1][1]==tab[2][2][2]))
				return a;
			if((tab[1][1][2]==a)&&(tab[0][0][3]==tab[1][1][2])&&(tab[2][2][1]==tab[3][3][0])&&(tab[3][3][0]==tab[1][1][2]))
				return a;
			if((tab[1][2][1]==a)&&(tab[0][3][0]==tab[1][2][1])&&(tab[2][1][2]==tab[3][0][3])&&(tab[2][1][2]==tab[1][2][1]))
				return a;
			if((tab[2][1][1]==a)&&(tab[0][3][3]==tab[1][2][2])&&(tab[2][1][1]==tab[3][0][0])&&(tab[3][0][0]==tab[2][1][1]))
				return a;
			//------------4 main diagonals----------------------------
			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					if(tab[i][j][j]!=a)
						break;
					if(j==3)
						return a;
				}//-----------4diagonals-----------
				for(int j=0;j<4;j++) {
					if(tab[i][j][3-j]!=a)
						break;
					if(j==3)
						return a;
				}//-----------4diagonals-----------
				//---------------------8diagonals----------------------
			}
		}
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				for(int k=0;k<4;k++) {
					if(tab[i][j][k]==0)
						return 0;
					if((i==3)&&(j==3)&&(k==3))
						return 3;
				}
			}
		}
		return 0;
	}
}

class listnode
{
	int tab[][][];
	int i,j,k;

	listnode next;
	int alpha,beta;

	listnode(int a[][][],int i,int j,int k) {
		tab=checkwin.copy(a);
		tab[i][j][k]=1;
	}
}

class list
{
	listnode first;
	static int wins[][][];
	list() {
		first=null;
		wins=checkwin.returnWins();
	}
	void add(listnode l) {
		listnode t;
		if(first==null) {
			first=l;
		}
		else {
			t=first;
			while(t.next!=null) {
				t=t.next;
			}
			l.next=null;
			t.next=l;
		}
	}
	void deleteList() {
		first=null;
	}

	void setMM() {
		int tab[][][],tab1[][][];
		int b[],a[]=new int[2];
		a[0]=a[1]=0;
		int w;
		listnode t=first;
		if(t!=null) {
			while(true) {
				tab=checkwin.copy(t.tab);
				for(int i=0;i<4;i++) {
					for(int j=0;j<4;j++) {
						for(int k=0;k<4;k++) {
							if(tab[i][j][k]==0) {
								tab1=checkwin.copy(tab);
								tab1[i][j][k]=2;
								w=checkwin.check(tab1);
								if(w==2)
									a[1]=50000;
								b=checkwin.checkMove(tab1);
									if(a[0]<b[0])
								a[0]=b[0];
									if(a[1]<b[1])
								a[1]=b[1];
							}
						}
					}
				}
				t.alpha=a[0];
				t.beta=a[1];
				if(t.next!=null)
					t=t.next;
				else
					break;
			}
		}
	}

	int[] decide()
	{
		setMM();
		int res[]=new int[3];
		int minb=-1,maxa=-1,maxb=-1,t1,t2;
		listnode t=first;
		maxa=t.alpha+t.beta;
		minb=maxb=t.beta;
		while(true) {
			t1=t.alpha;
			t2=t.beta;
			if(t2>maxb)
				maxb=t2;
			if(t2<minb)
				minb=t2;
			if((t1+t2)>maxa)
				maxa=t1+t2;

			if(t.next!=null)
				t=t.next;
			else
				break;
		}
		boolean best=true;
		if(maxb==50000)
			best=false;

		t=first;
		int maxa2=t.alpha+t.beta;
		while(true) {
			if(best) {
				t1=t.alpha;
				t2=t.beta;
				if((t1+t2)==maxa) {
					res[0]=t.i;
					res[1]=t.j;
					res[2]=t.k;
					return res;
				}
			}
			else {
				t1=t.alpha;
				t2=t.beta;
				if(t2!=50000) {
					if((t1+t2)>maxa2) {
						maxa2=t1+t2;
					}
				}
			}
			if(t.next!=null)
				t=t.next;
			else
				break;
		}
		t=first;
		while(true) {
			t1=t.alpha;
			t2=t.beta;
			if((t1+t2)==maxa2) {
				res[0]=t.i;
				res[1]=t.j;
				res[2]=t.k;
				return res;
			}
			if(t.next!=null)
				t=t.next;
			else
				break;
		}
		return res;
	}
}

class ttt3d
{
	static int wins[][][];
	static int pos[][][];
	int[][][] table;
	list tlist;
	void initpos() {
		pos=new int[4][4][4];
		int cnt=0;
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				for(int k=0;k<4;k++) {
					cnt=0;
					for(int l=0;l<76;l++) {
						for(int m=0;m<4;m++) {
							if((wins[l][m][0]==i)&&(wins[l][m][1]==j)&&(wins[l][m][2]==k))
								cnt++;
						}
					}
					pos[i][j][k]=cnt;
				}
			}
		}
	}
	void refreshList() {
		tlist=null;
	}
	ttt3d() {
		wins=checkwin.returnWins();
		initpos();
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++)
				for(int k=0;k<4;k++)
					table[i][j][k]=0;
	}
	ttt3d(int a[][][]) {
		wins=checkwin.returnWins();
		initpos();
		table=checkwin.copy(a);
	}

	int[] decision() {
		int fin[]=new int[3];
		int minc=9999,maxc=0,minp=9999,maxp=0,maxt=0;
		int t1,t2,temp[];
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				for(int k=0;k<4;k++) {
					if(table[i][j][k]==0) {
						temp=checkPos(table,i,j,k);
						t1=temp[0];
						t2=temp[1];
						if(t1>=9999) {
							fin[0]=i;
							fin[1]=j;
							fin[2]=k;
							return fin;
						}
						if((t1+t2)>maxt)
							maxt=t1+t2;
						if(t1<minc)
							minc=t1;
						if(t1>maxc)
							maxc=t1;
						if(t2<minp)
							minp=t2;
						if(t2>maxp)
							maxp=t2;
					}
				}
			}
		}
		if(maxp>=9999) {
			System.out.println("1st");
			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					for(int k=0;k<4;k++) {
						if(table[i][j][k]==0) {
							temp=checkPos(table,i,j,k);
							t2=temp[1];
							if(t2==maxp) {
								fin[0]=i;
								fin[1]=j;
								fin[2]=k;
								return fin;
							}
						}
					}
				}
			}
		}
		int max=1,ct=0;
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				for(int k=0;k<4;k++) {
					if(table[i][j][k]!=0) {
						ct++;
					}
				}
			}
		}
		if((maxc<=100)||(maxt<=300)||(ct<5)) {
			 System.out.println("2nd");
			 for(int i=0;i<4;i++) {
			 	for(int j=0;j<4;j++) {
			 		for(int k=0;k<4;k++) {
			 			if((table[i][j][k]==0)&&(pos[i][j][k]>max))
			 				max=pos[i][j][k];
					}
			 	}
			 }
			 int tp[]=new int[3];
			 for(int i=0;i<4;i++) {
			 	for(int j=0;j<4;j++) {
			 		for(int k=0;k<4;k++) {
			 			if((table[i][j][k]==0)&&(pos[i][j][k]==max)) {
			 				int cnt=0;
			 				s:for(int l=0;l<4;l++) {
			 					for(int m=0;m<4;m++) {
			 						for(int n=0;n<4;n++) {
			 							temp=checkPos(table,l,m,n);
			 							if(temp[0]==maxc) {
			 								tp[0]=l;
			 								tp[1]=m;
			 								tp[2]=n;
			 								break s;
			 							}
			 						}
			 					}
			 				}
			 				for(int l=0;l<76;l++) {
			 					for(int m=0;m<4;m++) {
			 						if((wins[l][m][0]==tp[0])&&(wins[l][m][1]==tp[1])&&(wins[l][m][2]==tp[2])) {
			 							for(int n=0;n<4;n++) {
			 								if((wins[l][n][0]==i)&&(wins[l][n][1]==j)&&(wins[l][n][2]==k)) {
			 									cnt++;
			 								}
			 							}
			 						}
			 					}
			  				}
			 				if(cnt>=2) {
			 					fin[0]=i;
			 					fin[1]=j;
			 					fin[2]=k;
			 					return fin;
			 				}
			 			}
			 		}
			 	}
			 }
			 System.out.println("end of 2nd");
		}
		tlist=new list();
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
			for(int k=0;k<4;k++) {
					if(table[i][j][k]==0) {
						listnode l=new listnode(table,i,j,k);
						tlist.add(l);
					}
				}
			}
		}
		System.out.println("3rd");
		fin=tlist.decide();
		refreshList();
		return fin;
	}

	int[] checkPos(int a[][][],int i,int j,int k)
	{
		int b[]=new int[2];
		int min,max,temp,cnt1,cnt2;
		int cnt11,cnt12,cnt21,cnt22,cnt31,cnt32;
		if(a[i][j][k]==0) {
			cnt31=cnt32=0;
			cnt21=cnt22=0;
			cnt11=cnt12=0;
			for(int l=0;l<76;l++) {
				for(int m=0;m<4;m++) {
					if((wins[l][m][0]==i)&&(wins[l][m][1]==j)&&(wins[l][m][2]==k)) {
						cnt1=cnt2=0;
						q:for(int n=0;n<4;n++) {
							temp=a[wins[l][n][0]][wins[l][n][1]][wins[l][n][2]];
							if(temp==1) {
								for(int p=0;p<4;p++) {
									temp=a[wins[l][p][0]][wins[l][p][1]][wins[l][p][2]];
									if(temp==2)
										break q;
								}
							cnt1++;
							}
							else if(temp==2) {
								for(int p=0;p<4;p++) {
									temp=a[wins[l][p][0]][wins[l][p][1]][wins[l][p][2]];
									if(temp==1)
										break q;
								}
							cnt2++;
							}
						}
						if(cnt1==1)
							cnt11++;
						else if(cnt1==2)
							cnt21++;
						else if(cnt1==3)
							cnt31++;
						
						if(cnt2==1)
							cnt12++;
						else if(cnt2==2)
							cnt22++;
						else if(cnt2==3)
							cnt32++;
					}
				}
			}

			min=9999*cnt31+99*cnt21+9*cnt11;
			max=10000*cnt32+100*cnt22+10*cnt12;
			b[0]=min;
			b[1]=max;
		}
		return b;
	}
}

class test
{

	static int[] returnMove(int x[][][]) {
		Random r1 = new Random();
		int g,c,r;
		g=r1.nextInt(4);
		c=r1.nextInt(4);
		r=r1.nextInt(4);
		int res[]=new int[3];
		//System.out.println("Grid no: "+g+" Col no: "+c+" Row no: "+r);

	boolean t=true;
	while(t)
	{
		if(x[g][c][r]==0)
		{
			res[0]=g;
			res[1]=c;
			res[2]=r;
			break;
		}
		else
		{
			g=r1.nextInt(4);
			c=r1.nextInt(4);
			r=r1.nextInt(4);
		}
	}
	return res;
	}
}
