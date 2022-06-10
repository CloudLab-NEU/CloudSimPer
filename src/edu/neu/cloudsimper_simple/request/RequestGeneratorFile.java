package edu.neu.cloudsimper_simple.request;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import edu.neu.cloudsimper_simple.Const;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public abstract class RequestGeneratorFile extends RequestGeneratorAbstract implements RequestGenerator {

	protected int[] traces;
	protected int size;

	public void setSize(int size) {
		this.size = size;
	}

	public final void start() {
		traces = new int[size];
		try {
			FileReader fr = new FileReader(Const.CONIF_FILE_REQUEST_DATA + this.getName() + Const.CONIF_SUFFIX_REQUEST);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			for (int i = 0; i < this.size; i++) {
				if ((line = br.readLine()) != null) {
					this.traces[i] = parseRequestNum(line);
				} else {
					this.traces[i] = 0;
				}
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected abstract int parseRequestNum(String line);

}
