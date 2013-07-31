package com.jajeem.util;

public class Query {

	public static String SummaryOfStudents(int quizRunId) {
		return "SELECT QUIZTITLE,QUIZCATEGORY,QUIZPOINTS,QUIZDATE,STUDENTID,STUDENTNAME,COUNT(ANSWERVALID) QUESTIONCOUNT,SUM(AV) VALIDANSWERS, SUM(AV)*100/COUNT(ANSWERVALID) PERCENTS,SUM(AV)*QUIZPOINTS/COUNT(ANSWERVALID)POINTS FROM ( "
				+ "SELECT Q.TITLE QUIZTITLE,Q.CATEGORY QUIZCATEGORY,Q.POINTS QUIZPOINTS,FORMATDATETIME(DATEADD('SECOND', QRN.START/1000, DATE '1970-01-01'),    'EEE, d MMM yyyy', 'en', 'GMT') QUIZDATE,QR.STUDENTID,CONCAT(S.FIRSTNAME,' ',S.MIDDLENAME,' ',S.LASTNAME) STUDENTNAME,QR.QUIZQUESTIONID,QR.ANSWERVALID, "
				+ "CASE ANSWERVALID WHEN FALSE THEN 0 WHEN TRUE THEN 1 END AV "
				+ "FROM QUIZREPONSE QR JOIN QUIZQUESTION QQ ON (QR.QUIZQUESTIONID=QQ.ID) "
				+ "JOIN QUIZ Q ON (QQ.QUIZID=Q.ID) "
				+ "JOIN QUIZRUN QRN ON (QQ.QUIZID=QRN.QUIZID) "
				+ "JOIN STUDENT S ON (QR.STUDENTID=S.ID) "
				+ "WHERE QRN.ID="
				+ quizRunId
				+ ") "
				+ "GROUP BY STUDENTID "
				+ "ORDER BY VALIDANSWERS ";
	}

	public static String StudentResult(int quizRunId, int studetId) {
		return "SELECT Q.TITLE QUIZTITLE,Q.CATEGORY QUIZCATEGORY,FORMATDATETIME(DATEADD('SECOND', QRN.START/1000, DATE 	'1970-01-01'), 'EEE, d MMM yyyy', 'en', 'GMT') QUIZDATE,QR.STUDENTID,CONCAT(S.FIRSTNAME,' ',S.MIDDLENAME,' ',S.LASTNAME) STUDENTNAME,QR.QUIZQUESTIONID,CASE QR.ANSWERVALID WHEN FALSE THEN 'Incorrect' WHEN TRUE THEN 'Correct' END RESULT,"
				+ "CASE WHEN QQ.TYPE<3 THEN "
				+ "CONCAT(CASE QQ.BOOL1 WHEN TRUE THEN '(1)' ELSE '' END,"
				+ "CASE QQ.BOOL2 WHEN TRUE THEN '(2)' ELSE '' END,"
				+ "CASE QQ.BOOL3 WHEN TRUE THEN '(3)' ELSE '' END,"
				+ "CASE QQ.BOOL4 WHEN TRUE THEN '(4)' ELSE '' END,"
				+ "CASE QQ.BOOL5 WHEN TRUE THEN '(5)' ELSE '' END)"
				+ "WHEN QQ.TYPE=3 THEN CONCAT(QQ.BOOL1,',',QQ.BOOL2,',',QQ.BOOL3,',',QQ.BOOL4,',',QQ.BOOL5) ELSE '' END CORRECTANSWER,"
				+ "CASE WHEN QQ.TYPE<3 THEN "
				+ "CONCAT(CASE QR.BOOL1 WHEN TRUE THEN '(1)' ELSE '' END,"
				+ "CASE QR.BOOL2 WHEN TRUE THEN '(2)' ELSE '' END,"
				+ "CASE QR.BOOL3 WHEN TRUE THEN '(3)' ELSE '' END,"
				+ "CASE QR.BOOL4 WHEN TRUE THEN '(4)' ELSE '' END,"
				+ "CASE QR.BOOL5 WHEN TRUE THEN '(5)' ELSE '' END) "
				+ "WHEN QQ.TYPE=3 THEN QR.ANSWER ELSE '' END ANSWER,"
				+ "(CASE QR.ANSWERVALID WHEN FALSE THEN 0 WHEN TRUE THEN 1 END)*QQ.POINT ANSWERPOINT "
				+ "FROM QUIZREPONSE QR JOIN QUIZQUESTION QQ ON (QR.QUIZQUESTIONID=QQ.ID) "
				+ "JOIN QUIZ Q ON (QQ.QUIZID=Q.ID) "
				+ "JOIN QUIZRUN QRN ON (QQ.QUIZID=QRN.QUIZID) "
				+ "JOIN STUDENT S ON (QR.STUDENTID=S.ID) "
				+ "WHERE QRN.ID="
				+ quizRunId
				+ " AND QR.STUDENTID="
				+ studetId
				+ " "
				+ "ORDER BY QR.QUIZQUESTIONID";
	}

	public static String PointChart(int quizRunId, boolean answerValidity) {
		return "(SELECT QUIZTITLE,QUIZCATEGORY,QUIZPOINTS,QUIZDATE,COUNT(*) STUDENTCOUNT, SUM(POINTSUM), CONCAT('0-',QUIZPOINTS/5) POINTRANGE FROM("
				+ "	SELECT Q.TITLE QUIZTITLE,Q.CATEGORY QUIZCATEGORY,Q.POINTS QUIZPOINTS, 			FORMATDATETIME(DATEADD('SECOND',QRN.START/1000,DATE'1970-01-01'),'EEE, d MMM yyyy','en', 'GMT') QUIZDATE,QR.STUDENTID,SUM(QQ.POINT) POINTSUM"
				+ "	FROM QUIZREPONSE QR JOIN QUIZQUESTION QQ ON (QR.QUIZQUESTIONID=QQ.ID)"
				+ "	    JOIN QUIZ Q ON (QQ.QUIZID=Q.ID)"
				+ "	    JOIN QUIZRUN QRN ON (QQ.QUIZID=QRN.QUIZID)"
				+ "	WHERE QRN.ID="
				+ quizRunId
				+ " AND QR.ANSWERVALID="
				+ answerValidity
				+ ""
				+ "	GROUP BY Q.TITLE,Q.CATEGORY,Q.POINTS, 			FORMATDATETIME(DATEADD('SECOND',QRN.START/1000,DATE'1970-01-01'),'EEE, d MMM yyyy','en', 'GMT'),QR.STUDENTID"
				+ "	) WHERE POINTSUM>0 AND POINTSUM<=QUIZPOINTS/5"
				+ ")"
				+ "UNION ALL"
				+ "("
				+ "SELECT QUIZTITLE,QUIZCATEGORY,QUIZPOINTS,QUIZDATE,COUNT(*) STUDENTCOUNT, SUM(POINTSUM),CONCAT((QUIZPOINTS/5)+1,'-',2*QUIZPOINTS/5) POINTRANGE FROM("
				+ "	SELECT Q.TITLE QUIZTITLE,Q.CATEGORY QUIZCATEGORY,Q.POINTS QUIZPOINTS, 			FORMATDATETIME(DATEADD('SECOND',QRN.START/1000,DATE'1970-01-01'),'EEE, d MMM yyyy','en', 'GMT') QUIZDATE,QR.STUDENTID,SUM(QQ.POINT) POINTSUM"
				+ "	FROM QUIZREPONSE QR JOIN QUIZQUESTION QQ ON (QR.QUIZQUESTIONID=QQ.ID)"
				+ "	    JOIN QUIZ Q ON (QQ.QUIZID=Q.ID)"
				+ "	    JOIN QUIZRUN QRN ON (QQ.QUIZID=QRN.QUIZID)"
				+ "	WHERE QRN.ID="
				+ quizRunId
				+ " AND QR.ANSWERVALID="
				+ answerValidity
				+ ""
				+ "	GROUP BY Q.TITLE,Q.CATEGORY,Q.POINTS, 			FORMATDATETIME(DATEADD('SECOND',QRN.START/1000,DATE'1970-01-01'),'EEE, d MMM yyyy','en', 'GMT'),QR.STUDENTID"
				+ "	) WHERE POINTSUM>QUIZPOINTS/5 AND POINTSUM<=2*QUIZPOINTS/5"
				+ ")"
				+ "UNION ALL"
				+ "("
				+ "SELECT QUIZTITLE,QUIZCATEGORY,QUIZPOINTS,QUIZDATE,COUNT(*) STUDENTCOUNT, SUM(POINTSUM),CONCAT((2*QUIZPOINTS/5)+1,'-',3*QUIZPOINTS/5) POINTRANGE FROM("
				+ "	SELECT Q.TITLE QUIZTITLE,Q.CATEGORY QUIZCATEGORY,Q.POINTS QUIZPOINTS, 			FORMATDATETIME(DATEADD('SECOND',QRN.START/1000,DATE'1970-01-01'),'EEE, d MMM yyyy','en', 'GMT') QUIZDATE,QR.STUDENTID,SUM(QQ.POINT) POINTSUM"
				+ "	FROM QUIZREPONSE QR JOIN QUIZQUESTION QQ ON (QR.QUIZQUESTIONID=QQ.ID)"
				+ "	    JOIN QUIZ Q ON (QQ.QUIZID=Q.ID)"
				+ "	    JOIN QUIZRUN QRN ON (QQ.QUIZID=QRN.QUIZID)"
				+ "	WHERE QRN.ID="
				+ quizRunId
				+ " AND QR.ANSWERVALID="
				+ answerValidity
				+ ""
				+ "	GROUP BY Q.TITLE,Q.CATEGORY,Q.POINTS, 			FORMATDATETIME(DATEADD('SECOND',QRN.START/1000,DATE'1970-01-01'),'EEE, d MMM yyyy','en', 'GMT'),QR.STUDENTID"
				+ "	) WHERE POINTSUM>2*QUIZPOINTS/5 AND POINTSUM<=3*QUIZPOINTS/5"
				+ ")"
				+ "UNION ALL"
				+ "("
				+ "SELECT QUIZTITLE,QUIZCATEGORY,QUIZPOINTS,QUIZDATE,COUNT(*) STUDENTCOUNT, SUM(POINTSUM),CONCAT((3*QUIZPOINTS/5)+1,'-',4*QUIZPOINTS/5) POINTRANGE FROM("
				+ "	SELECT Q.TITLE QUIZTITLE,Q.CATEGORY QUIZCATEGORY,Q.POINTS QUIZPOINTS, 			FORMATDATETIME(DATEADD('SECOND',QRN.START/1000,DATE'1970-01-01'),'EEE, d MMM yyyy','en', 'GMT') QUIZDATE,QR.STUDENTID,SUM(QQ.POINT) POINTSUM"
				+ "	FROM QUIZREPONSE QR JOIN QUIZQUESTION QQ ON (QR.QUIZQUESTIONID=QQ.ID)"
				+ "	    JOIN QUIZ Q ON (QQ.QUIZID=Q.ID)"
				+ "	    JOIN QUIZRUN QRN ON (QQ.QUIZID=QRN.QUIZID)"
				+ "	WHERE QRN.ID="
				+ quizRunId
				+ " AND QR.ANSWERVALID="
				+ answerValidity
				+ ""
				+ "	GROUP BY Q.TITLE,Q.CATEGORY,Q.POINTS, 			FORMATDATETIME(DATEADD('SECOND',QRN.START/1000,DATE'1970-01-01'),'EEE, d MMM yyyy','en', 'GMT'),QR.STUDENTID"
				+ "	) WHERE POINTSUM>3*QUIZPOINTS/5 AND POINTSUM<=4*QUIZPOINTS/5"
				+ ")"
				+ "UNION ALL"
				+ "("
				+ "SELECT QUIZTITLE,QUIZCATEGORY,QUIZPOINTS,QUIZDATE,COUNT(*) STUDENTCOUNT, SUM(POINTSUM),CONCAT((4*QUIZPOINTS/5)+1,'-',QUIZPOINTS) POINTRANGE FROM("
				+ "	SELECT Q.TITLE QUIZTITLE,Q.CATEGORY QUIZCATEGORY,Q.POINTS QUIZPOINTS, 			FORMATDATETIME(DATEADD('SECOND',QRN.START/1000,DATE'1970-01-01'),'EEE, d MMM yyyy','en', 'GMT') QUIZDATE,QR.STUDENTID,SUM(QQ.POINT) POINTSUM"
				+ "	FROM QUIZREPONSE QR JOIN QUIZQUESTION QQ ON (QR.QUIZQUESTIONID=QQ.ID)"
				+ "	    JOIN QUIZ Q ON (QQ.QUIZID=Q.ID)"
				+ "	    JOIN QUIZRUN QRN ON (QQ.QUIZID=QRN.QUIZID)"
				+ "	WHERE QRN.ID="
				+ quizRunId
				+ " AND QR.ANSWERVALID="
				+ answerValidity
				+ ""
				+ "	GROUP BY Q.TITLE,Q.CATEGORY,Q.POINTS, 			FORMATDATETIME(DATEADD('SECOND',QRN.START/1000,DATE'1970-01-01'),'EEE, d MMM yyyy','en', 'GMT'),QR.STUDENTID"
				+ "	) WHERE POINTSUM>4*QUIZPOINTS/5 AND POINTSUM<=QUIZPOINTS"
				+ ")";
	}

	public static String AnswerRate(int quizRunid) {
		return "SELECT Q.TITLE QUIZTITLE,Q.CATEGORY QUIZCATEGORY,FORMATDATETIME(DATEADD('SECOND',QRN.START/1000,DATE'1970-01-01'),'EEE, d MMM yyyy','en', 'GMT') QUIZDATE,QQ.ID,COUNT(QR.STUDENTID),SUM(QR.ANSWERVALID),SUM(QR.ANSWERVALID)*100/COUNT(QR.STUDENTID) CorrectAnswerPercent"
				+ " FROM QUIZREPONSE QR JOIN QUIZQUESTION QQ ON (QR.QUIZQUESTIONID=QQ.ID)"
				+ "    JOIN QUIZ Q ON (QQ.QUIZID=Q.ID)"
				+ "    JOIN QUIZRUN QRN ON (QQ.QUIZID=QRN.QUIZID)"
				+ " WHERE QRN.ID="
				+ quizRunid
				+ ""
				+ " GROUP BY Q.TITLE,Q.CATEGORY,FORMATDATETIME(DATEADD('SECOND',QRN.START/1000,DATE'1970-01-01'),'EEE, d MMM yyyy','en', 'GMT'),QQ.ID"
				+ " ORDER BY QQ.ID";
	}
}
