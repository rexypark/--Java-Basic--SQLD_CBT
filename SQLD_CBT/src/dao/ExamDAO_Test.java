package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import vo.ExamVO;

public class ExamDAO_Test {

   public static void main(String[] args) {
      String oX = "";// 한문제당 정답 유무에 대한 값 저장
      
      ExamDAO dao = new ExamDAO();
      List<ExamVO> list = dao.selectAll();
      for (ExamVO mvo : list ) {
      System.out.println("===========================");
      System.out.println(mvo.getQwestion());//문제
      
      System.out.println("---------------------------");
      Scanner scan = new Scanner(System.in);
      
      System.out.print("문자열 입력 : ");   
      
      String str1 = scan.nextLine();
      
      if(str1.equals(mvo.getAnswer())) {
         System.out.println("정답이다");
         oX = "o"; 
      }else {
         System.out.println("땡!!!!");
         oX = "x";
      }
      System.out.println("정답은 : " + mvo.getAnswer());
      System.out.println("---------------------------");
      
      
      ExamDAO.insertOne(mvo ,oX);
      System.out.println("완료");
      }

   }

}