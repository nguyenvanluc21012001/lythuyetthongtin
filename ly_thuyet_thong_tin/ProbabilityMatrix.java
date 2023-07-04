package ly_thuyet_thong_tin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ProbabilityMatrix {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		// Nhập kích thước ma trận
		System.out.print("Nhap so hang M: ");
		int M = scanner.nextInt();
		System.out.print("Nhap so cot N: ");
		int N = scanner.nextInt();

		// Khởi tạo ma trận xác suất
		double[][] matrix = new double[M][N];
		double luc = 1;
		// Nhập giá trị xác suất từng phần tử của ma trận
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				double probability;
				do {
					System.out.println("Nhap gia trị xac xuat P(" + i + "," + j + "): ");
					System.out.printf("Gia tri tu 0 -> " + luc + " :");
					probability = scanner.nextDouble();
					if (probability < 0 || probability > luc) {
						System.out.println("Xac xuat khong the la so am . Vui long nhap lai.");
					}
				} while (probability < 0 || probability > luc);

				matrix[i][j] = probability;
				luc -= probability;
			}
		}
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}

		// double[][] matrix = { { 0.125, 0.0625, 0.03125, 0.03125 }, { 0.0625, 0.125,
		// 0.03125, 0.03125 },
		// { 0.0625, 0.0625, 0.0625, 0.0625 }, { 0.25, 0, 0, 0 } };
		// danh sách giá trị có trong mảng
		List<Double> c = findUniqueValues(matrix);

		// tính giá trị của H(X,Y)
		double H_X_Y = 0;
		for (Double phantu : c) {
			int lanXuatHien = lanXuatHien(matrix, phantu);
			H_X_Y -= lanXuatHien * phantu * (Math.log(phantu) / Math.log(2));
			System.out.println(H_X_Y);
		}
		System.out.println("gia tri cua H_X_Y la : " + H_X_Y);

		// tính giá trị của H(Y)
		double[] Y = new double[M];
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				Y[i] += matrix[i][j];
			}

		}
		System.out.println("mang cua Y la : ");
		for (int i = 0; i < M; i++) {
			System.out.printf((Y[i] + " "));
		}
		// danh sach phan tu khac nhau trong mang_y
		List<Double> c1 = findUniqueValues2(Y);
		double H_Y = 0;
		for (Double phantu1 : c1) {
			int lanXuatHien = lanXuatHien2(Y, phantu1);
			System.out.println("so lan xuat hien cua " + phantu1 + " la " + lanXuatHien);
			H_Y -= lanXuatHien * phantu1 * (Math.log(phantu1) / Math.log(2));
		}
		System.out.println("gia tri của H_Y la : " + H_Y);

		// tính giá trị của H(X)
		double[] X = new double[N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				X[i] += matrix[j][i];
			}

		}
		System.out.println("mang cua X la : ");
		for (int i = 0; i < M; i++) {
			System.out.printf((X[i] + " "));
		}
		// danh sach phan tu khac nhau trong mang_y
		List<Double> c2 = findUniqueValues2(X);
		double H_X = 0;
		for (Double phantu2 : c2) {
			int lanXuatHien = lanXuatHien2(X, phantu2);
			System.out.println("so lan xuat hien cua " + phantu2 + " la " + lanXuatHien);
			H_X -= lanXuatHien * phantu2 * (Math.log(phantu2) / Math.log(2));
		}
		double D1=0;
		double k=0;
		for(int i=0;i<M;i++) {
			k=X[i]*Math.log((double)(X[i]/Y[i]));
			System.out.println(k);
			D1+=k;
		}
		double D2=0;
		for(int i=0;i<M;i++) {
			k=Y[i]*Math.log((double)(Y[i]/X[i]));
			System.out.println(k);
			D2+=k;
		}
		System.out.println("gia tri cua H_X la : " + H_X);
		System.out.println("gia tri cua H_Y la : " + H_Y);
		System.out.println("gia trị cua H(X/Y) : " + (H_X_Y - H_Y));
		System.out.println("gia trị cua H(Y/X) : " + (H_X_Y - H_X));
		System.out.println("gia trị của H(Y)-H(Y/X) :" + (H_Y + H_X - H_X_Y));
		System.out.println("gia trị của I(X:Y) :" + (H_Y + H_X - H_X_Y));
		System.out.println("gia trị của D(P(X)||P(Y)) : "+D1);
		System.out.println("gia trị của D(P(Y)||P(X)) : "+D2);
	}

	// tìm danh sách khác nhau trong mảng 2 chiều
	private static List<Double> findUniqueValues(double[][] array) {
		List<Double> uniqueValues = new ArrayList<>();

		for (double[] row : array) {
			for (double value : row) {
				if (!uniqueValues.contains(value)) {
					uniqueValues.add(value);
				}
			}
		}
		uniqueValues.remove(0.0);
		return uniqueValues;
	}

	// số lần xuất hiện phần tử trong mảng 2 chiều
	private static int lanXuatHien(double[][] array, double c) {
		int count = 0;

		for (double[] row : array) {
			for (double value : row) {
				if (value == c) {
					count++;
				}
			}
		}
		return count;
	}

	// tìm danh sách khác nhau trong mảng 1 chiều
	private static List<Double> findUniqueValues2(double[] array) {
		List<Double> uniqueValues = new ArrayList<>();

		for (double value : array) {
			if (!uniqueValues.contains(value)) {
				uniqueValues.add(value);
			}
		}

		return uniqueValues;
	}

	// số lần xuất hiện phần tử trong mảng 1 chiều
	private static int lanXuatHien2(double[] array, double c) {
		int count = 0;
		for (double value : array) {
			if (value == c) {
				count++;
			}
		}
		return count;
	}

}