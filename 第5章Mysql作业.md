1.查询所有员工的姓名、邮箱和工作岗位。

SELECT first_name, last_name, email, job_title
FROM employees;
2.查询所有部门的名称和位置。
SELECT dept_name, location
FROM departments;
3.查询工资超过70000的员工生名和工资。
SELECT first_name, last_name, salary
FROM employees
WHERE salary > 70000;
4.查询IT部门的所有员工。
SELECT e.first_name, e.last_name
FROM employees e
JOIN departments d ON e.dept_id = d.dept_id
WHERE d.dept_name = 'IT';
5.查询入职日期在2020年之后的员工信息。
SELECT *
FROM employees
WHERE hire_date > '2020-01-01';
6.计算每个部门的平均工资。
SELECT d.dept_name, AVG(e.salary) AS average_salary
FROM employees e
JOIN departments d ON e.dept_id = d.dept_id
GROUP BY d.dept_name;
7.查询工资最高的前3名员工信息。
SELECT *
FROM employees
ORDER BY salary DESC
LIMIT 3;
8.查询每个部门员工数量。
SELECT d.dept_name, COUNT(e.emp_id) AS employee_count
FROM employees e
JOIN departments d ON e.dept_id = d.dept_id
GROUP BY d.dept_name;
9.查询没有分配部门的员工。
SELECT *
FROM employees
WHERE dept_id IS NULL;
10.查询参与项目数量最多的员工。
SELECT e.first_name, e.last_name, COUNT(ep.project_id) AS project_count
FROM employees e
JOIN employee_projects ep ON e.emp_id = ep.emp_id
GROUP BY e.emp_id
ORDER BY project_count DESC
LIMIT 1;
11.计算所有员工的工资总和。
SELECT SUM(salary) AS total_salary
FROM employees;
12.查询姓"Smith”的员工信息。
SELECT *
FROM employees
WHERE last_name = 'Smith';SELECT *
FROM employees
WHERE last_name = 'Smith';
13.查询即将在半年内到期的项目。
SELECT *
FROM projects
WHERE end_date BETWEEN '2024-10-20' AND DATE_ADD('2024-10-20', INTERVAL 6 MONTH);
14.查询至少参与了两个项目的员工。
SELECT e.first_name, e.last_name
FROM employees e
JOIN employee_projects ep ON e.emp_id = ep.emp_id
GROUP BY e.emp_id
HAVING COUNT(ep.project_id) >= 2;
15.查询没有参与任何项目的员工。
SELECT *
FROM employees e
LEFT JOIN employee_projects ep ON e.emp_id = ep.emp_id
WHERE ep.project_id IS NULL;
16.计算每个项目参与的员工数量。
SELECT p.project_name, COUNT(ep.emp_id) AS employee_count
FROM projects p
JOIN employee_projects ep ON p.project_id = ep.project_id
GROUP BY p.project_name;
17.查询工资第二高的员工信息。
SELECT *
FROM employees
WHERE salary = (SELECT MAX(salary) FROM employees WHERE salary < (SELECT MAX(salary) FROM employees));
18.查询每个部门工资最高的员工。
SELECT e.first_name, e.last_name, e.salary, d.dept_name
FROM employees e
JOIN departments d ON e.dept_id = d.dept_id
WHERE (e.dept_id, e.salary) IN (SELECT dept_id, MAX(salary) FROM employees GROUP BY dept_id);
19.计算每个部门的工资总和1,并按照工资总和降序排列。
SELECT d.dept_name, SUM(e.salary) AS total_salary
FROM employees e
JOIN departments d ON e.dept_id = d.dept_id
GROUP BY d.dept_name
ORDER BY total_salary DESC;
20.查询员工姓名、部门名称和工资。
SELECT e.first_name, e.last_name, d.dept_name, e.salary
FROM employees e
JOIN departments d ON e.dept_id = d.dept_id;
21.查询每个员工的上级主管(假设empid小的是上级
SELECT e1.first_name AS employee_first_name, e1.last_name AS employee_last_name, e2.first_name AS manager_first_name, e2.last_name AS manager_last_name
FROM employees e1
LEFT JOIN employees e2 ON e1.emp_id > e2.emp_id
WHERE e2.emp_id = (SELECT MIN(emp_id) FROM employees WHERE emp_id < e1.emp_id);
22.查询所有员工的工作岗位,不要重复。
SELECT DISTINCT job_title
FROM employees;
23.查询平均工资最高的部门。
SELECT d.dept_name, AVG(e.salary) AS average_salary FROM employees e JOIN departments d ON e.dept_id = d.dept_id GROUP BY d.dept_name ORDER BY average_salary DESC LIMIT 1;
24.查询工资高于其所在部门平均工资的员工。
SELECT e1.first_name, e1.last_name, e1.salary, d.dept_name FROM employees e1 JOIN departments d ON e1.dept_id = d.dept_id WHERE e1.salary > ( SELECT AVG(e2.salary) FROM employees e2 WHERE e2.dept_id = e1.dept_id );
25.查询每个部门工资前两名的员工。
SELECT e.first_name, e.last_name, e.salary, d.dept_name
FROM employees e
JOIN departments d ON e.dept_id = d.dept_id
WHERE (e.dept_id, e.salary) IN (
    SELECT dept_id, salary
    FROM (
        SELECT dept_id, salary, ROW_NUMBER() OVER (PARTITION BY dept_id ORDER BY salary DESC) AS rn
        FROM employees
    ) sub
    WHERE rn <= 2
)
ORDER BY d.dept_name, e.salary DESC;

1.查询所有学生的信息。
SELECT * FROM student;
2.查询所有课程的信息。
SELECT * FROM course;
3.查询所有学生的姓名、学号和班级。
SELECT name, student_id, my_class FROM student;
4.查询所有教师的姓名和职称。
SELECT name, title FROM teacher;
5.查询不同课程的平均分数。
SELECT course_id, AVG(score) AS average_score FROM score GROUP BY course_id;
6.查询每个学生的平均分数。
SELECT student_id, AVG(score) AS average_score FROM score GROUP BY student_id;
7.查询分数大于85分的学生学号和课程号。
SELECT student_id, course_id FROM score WHERE score > 85;
8.查询每门课程的选课人数。
SELECT course_id, COUNT(student_id) AS student_count FROM score GROUP BY course_id;
9.查询选修了"高等数学"课程的学生姓名和分数。
SELECT s.name, sc.score FROM student s JOIN score sc ON s.student_id = sc.student_id JOIN course c ON sc.course_id = c.course_id WHERE c.course_name = '高等数学';
10.查询没有选修"大学物理"课程的学生姓名。
SELECT s.name FROM student s LEFT JOIN score sc ON s.student_id = sc.student_id LEFT JOIN course c ON sc.course_id = c.course_id WHERE c.course_name != '大学物理' OR c.course_name IS NULL;
11.查询C001比C002课程成绩高的学生信息及课程分数。
SELECT s1.name, s1.score AS C001_score, s2.score AS C002_score FROM ( SELECT s.name, sc.score FROM student s JOIN score sc ON s.student_id = sc.student_id WHERE sc.course_id = 'C001' ) s1 JOIN ( SELECT s.name, sc.score FROM student s JOIN score sc ON s.student_id = sc.student_id WHERE sc.course_id = 'C002' ) s2 ON s1.name = s2.name WHERE s1.score > s2.score;
12.统计各科成绩各分数段人数：课程编号，课程名称，[100-85]，[85-70]，[70-60]，[60-0] 及所占百分比
SELECT c.course_id, c.course_name, SUM(CASE WHEN sc.score BETWEEN 85 AND 100 THEN 1 ELSE 0 END) AS [100-85], SUM(CASE WHEN sc.score BETWEEN 70 AND 85 THEN 1 ELSE 0 END) AS [85-70], SUM(CASE WHEN sc.score BETWEEN 60 AND 70 THEN 1 ELSE 0 END) AS [70-60], SUM(CASE WHEN sc.score BETWEEN 0 AND 60 THEN 1 ELSE 0 END) AS [60-0], (SUM(CASE WHEN sc.score BETWEEN 85 AND 100 THEN 1 ELSE 0 END) / COUNT(sc.score)) * 100 AS [100-85]_percent, (SUM(CASE WHEN sc.score BETWEEN 70 AND 85 THEN 1 ELSE 0 END) / COUNT(sc.score)) * 100 AS [85-70]_percent, (SUM(CASE WHEN sc.score BETWEEN 60 AND 70 THEN 1 ELSE 0 END) / COUNT(sc.score)) * 100 AS [70-60]_percent, (SUM(CASE WHEN sc.score BETWEEN 0 AND 60 THEN 1 ELSE 0 END) / COUNT(sc.score)) * 100 AS [60-0]_percent FROM score sc JOIN course c ON sc.course_id = c.course_id GROUP BY c.course_id, c.course_name;
13.查询选择C002课程但没选择C004课程的成绩情况(不存在时显示为 null )。
SELECT s.name, sc1.score AS C002_score, sc2.score AS C004_score FROM student s LEFT JOIN score sc1 ON s.student_id = sc1.student_id AND sc1.course_id = 'C002' LEFT JOIN score sc2 ON s.student_id = sc2.student_id AND sc2.course_id = 'C004' WHERE sc1.score IS NOT NULL AND sc2.score IS NULL;
14.查询平均分数最高的学生姓名和平均分数。
SELECT s.name, AVG(sc.score) AS average_score FROM student s JOIN score sc ON s.student_id = sc.student_id GROUP BY s.student_id ORDER BY average_score DESC LIMIT 1;
15.查询总分最高的前三名学生的姓名和总分。
SELECT s.name, SUM(sc.score) AS total_score FROM student s JOIN score sc ON s.student_id = sc.student_id GROUP BY s.student_id ORDER BY total_score DESC LIMIT 3;
16. 查询各科成绩最高分、最低分和平均分。要求如下：
以如下形式显示：课程 ID，课程 name，最高分，最低分，平均分，及格率，中等率，优良率，优秀率
及格为>=60，中等为：70-80，优良为：80-90，优秀为：>=90
要求输出课程号和选修人数，查询结果按人数降序排列，若人数相同，按课程号升序排列
SELECT c.course_id, c.course_name, MAX(sc.score) AS highest_score, MIN(sc.score) AS lowest_score, AVG(sc.score) AS average_score, (SUM(CASE WHEN sc.score >= 60 THEN 1 ELSE 0 END) / COUNT(sc.score)) * 100 AS pass_rate, (SUM(CASE WHEN sc.score BETWEEN 70 AND 80 THEN 1 ELSE 0 END) / COUNT(sc.score)) * 100 AS medium_rate, (SUM(CASE WHEN sc.score BETWEEN 80 AND 90 THEN 1 ELSE 0 END) / COUNT(sc.score)) * 100 AS good_rate, (SUM(CASE WHEN sc.score >= 90 THEN 1 ELSE 0 END) / COUNT(sc.score)) * 100 AS excellent_rate FROM score sc JOIN course c ON sc.course_id = c.course_id GROUP BY c.course_id, c.course_name ORDER BY COUNT(sc.student_id) DESC, c.course_id ASC;

17.查询男生和女生的人数。
SELECT gender, COUNT(*) AS count FROM student GROUP BY gender;
18.查询年龄最大的学生姓名。
SELECT name FROM student ORDER BY birth_date ASC LIMIT 1;
19.查询年龄最小的教师姓名。
SELECT name FROM teacher ORDER BY birth_date DESC LIMIT 1;
20.查询学过「张教授」授课的同学的信息。
SELECT s.* FROM student s JOIN score sc ON s.student_id = sc.student_id JOIN course c ON sc.course_id = c.course_id JOIN teacher t ON c.teacher_id = t.teacher_id WHERE t.name = '张教授';
21.查询查询至少有一门课与学号为"2021001"的同学所学相同的同学的信息 。
SELECT s.* FROM student s JOIN score sc ON s.student_id = sc.student_id WHERE sc.course_id IN ( SELECT course_id FROM score WHERE student_id = '2021001' ) AND s.student_id != '2021001';
22.查询每门课程的平均分数，并按平均分数降序排列。
SELECT course_id, AVG(score) AS average_score FROM score GROUP BY course_id ORDER BY average_score DESC;
23.查询学号为"2021001"的学生所有课程的分数。
SELECT sc.course_id, c.course_name, sc.score FROM score sc JOIN course c ON sc.course_id = c.course_id WHERE sc.student_id = '2021001';
24.查询所有学生的姓名、选修的课程名称和分数。
SELECT s.name, c.course_name, sc.score FROM student s JOIN score sc ON s.student_id = sc.student_id JOIN course c ON sc.course_id = c.course_id;
25.查询每个教师所教授课程的平均分数。
SELECT t.name, c.course_name, AVG(sc.score) AS average_score FROM teacher t JOIN course c ON t.teacher_id = c.teacher_id JOIN score sc ON c.course_id = sc.course_id GROUP BY t.name, c.course_name;
22.查询分数在80到90之间的学生姓名和课程名称。
SELECT s.name, c.course_name FROM student s JOIN score sc ON s.student_id = sc.student_id JOIN course c ON sc.course_id = c.course_id WHERE sc.score BETWEEN 80 AND 90;
23.查询每个班级的平均分数。
SELECT s.my_class, AVG(sc.score) AS average_score FROM student s JOIN score sc ON s.student_id = sc.student_id GROUP BY s.my_class;
24.查询没学过"王讲师"老师讲授的任一门课程的学生姓名。
SELECT s.name FROM student s WHERE s.student_id NOT IN ( SELECT sc.student_id FROM score sc JOIN course c ON sc.course_id = c.course_id JOIN teacher t ON c.teacher_id = t.teacher_id WHERE t.name = '王讲师' );
25.查询两门及其以上小于85分的同学的学号，姓名及其平均成绩 。
SELECT s.student_id, s.name, AVG(sc.score) AS average_score FROM student s JOIN score sc ON s.student_id = sc.student_id GROUP BY s.student_id HAVING SUM(CASE WHEN sc.score < 85 THEN 1 ELSE 0 END) >= 2;
26.查询所有学生的总分并按降序排列。
SELECT s.student_id, s.name, SUM(sc.score) AS total_score FROM student s JOIN score sc ON s.student_id = sc.student_id GROUP BY s.student_id ORDER BY total_score DESC;
27.查询平均分数超过85分的课程名称。
SELECT c.course_name FROM course c JOIN score sc ON c.course_id = sc.course_id GROUP BY c.course_id HAVING AVG(sc.score) > 85;
28.查询每个学生的平均成绩排名。
SELECT s.student_id, s.name, AVG(sc.score) AS average_score, RANK() OVER (ORDER BY AVG(sc.score) DESC) AS rank FROM student s JOIN score sc ON s.student_id = sc.student_id GROUP BY s.student_id;
29.查询每门课程分数最高的学生姓名和分数。
SELECT c.course_id, c.course_name, s.name, sc.score FROM course c JOIN score sc ON c.course_id = sc.course_id JOIN student s ON sc.student_id = s.student_id WHERE (c.course_id, sc.score) IN ( SELECT course_id, MAX(score) FROM score GROUP BY course_id );
30.查询选修了"高等数学"和"大学物理"的学生姓名。
SELECT s.name FROM student s JOIN score sc1 ON s.student_id = sc1.student_id JOIN course c1 ON sc1.course_id = c1.course_id JOIN score sc2 ON s.student_id = sc2.student_id JOIN course c2 ON sc2.course_id = c2.course_id WHERE c1.course_name = '高等数学' AND c2.course_name = '大学物理';
31.按平均成绩从高到低显示所有学生的所有课程的成绩以及平均成绩（没有选课则为空）。
SELECT s.student_id, s.name, c.course_id, c.course_name, sc.score, AVG(sc.score) OVER (PARTITION BY s.student_id) AS average_score FROM student s CROSS JOIN course c LEFT JOIN score sc ON s.student_id = sc.student_id AND c.course_id = sc.course_id ORDER BY AVG(sc.score) OVER (PARTITION BY s.student_id) DESC, s.student_id, c.course_id;
32.查询分数最高和最低的学生姓名及其分数。
SELECT s.name, sc.score FROM student s JOIN score sc ON s.student_id = sc.student_id WHERE sc.score = (SELECT MAX(score) FROM score) UNION ALL SELECT s.name, sc.score FROM student s JOIN score sc ON s.student_id = sc.student_id WHERE sc.score = (SELECT MIN(score) FROM score);
33.查询每个班级的最高分和最低分。
SELECT s.my_class, MAX(sc.score) AS highest_score, MIN(sc.score) AS lowest_score FROM student s JOIN score sc ON s.student_id = sc.student_id GROUP BY s.my_class;
34.查询每门课程的优秀率（优秀为90分）。
SELECT c.course_id, c.course_name, (SUM(CASE WHEN sc.score >= 90 THEN 1 ELSE 0 END) / COUNT(sc.score)) * 100 AS excellent_rate FROM score sc JOIN course c ON sc.course_id = c.course_id GROUP BY c.course_id, c.course_name;
35.查询平均分数超过班级平均分数的学生。
SELECT s.student_id, s.name, AVG(sc.score) AS average_score FROM student s JOIN score sc ON s.student_id = sc.student_id GROUP BY s.student_id HAVING AVG(sc.score) > ( SELECT AVG(sc2.score) FROM student s2 JOIN score sc2 ON s2.student_id = sc2.student_id WHERE s2.my_class = s.my_class );
36.查询每个学生的分数及其与课程平均分的差值。
SELECT s.student_id, s.name, c.course_id, c.course_name, sc.score, sc.score - AVG(sc.score) OVER (PARTITION BY c.course_id) AS difference_from_average FROM student s JOIN score sc ON s.student_id = sc.student_id JOIN course c ON sc.course_id = c.course_id;

37.查询至少有一门课程分数低于80分的学生姓名。
SELECT s.name FROM student s JOIN score sc ON s.student_id = sc.student_id WHERE sc.score < 80 GROUP BY s.student_id;

38.查询所有课程分数都高于85分的学生姓名。
SELECT s.name FROM student s WHERE NOT EXISTS ( SELECT 1 FROM score sc WHERE sc.student_id = s.student_id AND sc.score <= 85 );
39.查询查询平均成绩大于等于90分的同学的学生编号和学生姓名和平均成绩。
SELECT s.student_id, s.name, AVG(sc.score) AS average_score FROM student s JOIN score sc ON s.student_id = sc.student_id GROUP BY s.student_id HAVING AVG(sc.score) >= 90;
40.查询选修课程数量最少的学生姓名。
SELECT s.name FROM student s JOIN ( SELECT student_id, COUNT(course_id) AS course_count FROM score GROUP BY student_id ) sc ON s.student_id = sc.student_id WHERE sc.course_count = ( SELECT MIN(course_count) FROM ( SELECT COUNT(course_id) AS course_count FROM score GROUP BY student_id ) sub );
41.查询每个班级的第2名学生（按平均分数排名）。
SELECT s.my_class, s.name, s.student_id, s.average_score FROM ( SELECT s.student_id, s.name, s.my_class, AVG(sc.score) AS average_score, RANK() OVER (PARTITION BY s.my_class ORDER BY AVG(sc.score) DESC) AS rank FROM student s JOIN score sc ON s.student_id = sc.student_id GROUP BY s.student_id ) sub WHERE sub.rank = 2;
42.查询每门课程分数前三名的学生姓名和分数。
SELECT c.course_id, c.course_name, s.name, sc.score FROM score sc JOIN student s ON sc.student_id = s.student_id JOIN course c ON sc.course_id = c.course_id WHERE (sc.course_id, sc.score) IN ( SELECT course_id, score FROM ( SELECT course_id, score, ROW_NUMBER() OVER (PARTITION BY course_id ORDER BY score DESC) AS rn FROM score ) sub WHERE sub.rn <= 3 ) ORDER BY c.course_id, sc.score DESC;