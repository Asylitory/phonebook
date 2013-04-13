<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 5.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/index.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<title>${form.title}</title>
</head>
<body>
<table id="wrap">
	<tr id="header">
		<td class="sideBorder" rowspan="3">
		</td>
		<td>
			<h2>Phonebook</h2>
		</td>
		<td class="sideBorder" rowspan="3">
		</td>
	</tr>
	<tr id="body">
		<td>
			<c:choose>
				<c:when test="${form.person != null}">
					<c:if test="${form.errors != null}">
						<table id="errors">
							<tr>
								<th>
									Обнаружены следующие ошибки:
								</th>
							</tr>
							<c:forEach var="str" items="${form.errors}">
								<tr>
									<td>
										${str}
									</td>
								</tr>
							</c:forEach>
						</table>		
					</c:if>
					<c:choose>
						<c:when test="${form.action == 'SHOW'}">
							<table class="entries">
								<tr>
									<td>
										Фамилия
									</td>
									<td>
										${form.person.lastname}
									</td>
								</tr>
								<tr>
									<td>
										Имя
									</td>
									<td>
										${form.person.firstname}
									</td>
								</tr>
								<tr>
									<td>
										Отчество
									</td>
									<td>
										${form.person.patronymic}
									</td>
								</tr>
								<tr>
									<td>
										Моб. телефон
									</td>
									<td>
										${form.person.phone_mobile}
									</td>
								</tr>
								<tr>
									<td>
										Домашний телефон
									</td>
									<td>
										${form.person.phone_home}
									</td>
								</tr>
								<tr>
									<td>
										Адрес
									</td>
									<td>
										${form.person.address}
									</td>
								</tr>
								<tr>
									<td>
										Электронная почта
									</td>
									<td>
										${form.person.email}
									</td>
								</tr>
							</table>
							<form action="${pageContext.request.contextPath}/index" method="POST">
								<input type="hidden" name="id" value="${form.person.id}">
								<table class="controls">
									<tr>
										<td>Редактировать</td>
										<td>Удалить</td>
										<td>Вернуться</td>
									</tr>
									<tr>
										<td><input type="submit" name="EDIT" value=">>"></td>
										<td><input type="submit" name="DELETE" value=">>"></td>
										<td><input type="submit" name="CANCEL" value=">>"></td>
									</tr>
								</table>
							</form>
						</c:when>
						<c:when test="${form.action == 'DELETE'}">
							<form action="${pageContext.request.contextPath}/index" method="POST">
								<input type="hidden" name="id" value="${form.person.id}">
								<table class="controls">
									<tr>
										<th colspan="2">Подтвердите удаление записи: ${form.person.lastname} ${form.person.firstname}</th>
									</tr>
									<tr>
										<td>Продолжить</td>
										<td>Отмена</td>
									</tr>
									<tr>
										<td><input type="submit" name="CONFIRMDELETE" value=">>"></td>
										<td><input type="submit" name="CANCEL" value=">>"></td>
									</tr>
								</table>
							</form>
						</c:when>
						<c:otherwise>
							<form action="${pageContext.request.contextPath}/index" method="POST">
								<input type="hidden" name="id" value="${form.person.id}">
								<table class="entries">
									<tr>
										<td>Фамилия</td>
										<td><input type="text" name="lastname" value="${form.person.lastname}"></td>
									</tr>
									<tr>
										<td>Имя</td>
										<td><input type="text" name="firstname" value="${form.person.firstname}"></td>
									</tr>
									<tr>
										<td>Отчество</td>
										<td><input type="text" name="patronymic" value="${form.person.patronymic}"></td>
									</tr>
									<tr>
										<td>Моб. телефон</td>
										<td><input type="text" name="phone_mobile" value="${form.person.phone_mobile}"></td>
									</tr>
									<tr>
										<td>Домашний телефон</td>
										<td><input type="text" name="phone_home" value="${form.person.phone_home}"></td>
									</tr>
									<tr>
										<td>Адрес</td>
										<td><input type="text" name="address" value="${form.person.address}"></td>
									</tr>
									<tr>
										<td>Электронная почта</td>
										<td><input type="text" name="email" value="${form.person.email}"></td>
									</tr>
								</table>
								<table class="controls">
									<tr>
										<td>Продолжить</td>
										<td>Отменить</td>
									</tr>
									<tr>
										<td><input type="submit" name="CONFIRM" value=">>"></td>
										<td><input type="submit" name="CANCEL" value=">>"></td>
									</tr>
								</table>
							</form>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${form.action == 'DELETE_ALL'}">
							<form action="${pageContext.request.contextPath}/index" method="POST">
								<table class="entries">
									<tr>
										<th>Будут удалены записи:</th>
									</tr>
									<c:forEach var="person" items="${form.personsList}">
										<tr>
											<td>${person.lastname} ${person.firstname} ${person.patronymic}</td>
										</tr>
										<input type="hidden" name="id=${person.id}">
									</c:forEach>
								</table>
								<table class="controls">
									<tr>
										<td>Удалить</td>
										<td>Отмена</td>
									</tr>
									<tr>
										<td><input type="submit" name="CONFIRM_DELETE_ALL" value=">>"></td>
										<td><input type="submit" name="CANCEL" value=">>">
									</tr>
								</table>
							</form>
						</c:when>
						<c:otherwise>
							<table>
								<tr>
									<th></th>
									<th>Ф.И.О.</th>
									<th>Моб. телефон</th>
									<th>Просмотреть</th>
									<th>Редактировать</th>
									<th>Удалить</th>
								</tr>
								<c:forEach var="person" items="${form.personsList}">
									<tr>
										<td><input type="checkbox" onclick="uncheckSelectAll(this)" class="onDelete" form="massDelete" name="id=${person.id}"></td>
										<form action="${pageContext.request.contextPath}/index" method="POST">
											<input type="hidden" name="id" value="${person.id}"> 
											<td>${person.lastname} ${person.firstname} ${person.patronymic}</td>
											<td>${person.phone_mobile}</td>
											<td class="buttons"><input type="submit" name="SHOW" value=">>"></td>
											<td class="buttons"><input type="submit" name="EDIT" value=">>"></td>
											<td class="buttons"><input type="submit" name="DELETE" value=">>"></td>
										</form>
									</tr>
								</c:forEach>
								<form id="massDelete" action="${pageContext.request.contextPath}/index" method="POST">
									<tr class="buttons">
										<td><input type="checkbox" onclick="switchAll(this)" id="selectAll"></td>
										<td colspan="3">Удалить отмеченные записи?</td>
										<td colspan="2"><input type="submit" name="DELETE_ALL" value=">>"></td>
									</tr>
								</form>
							</table>
						<form action="${pageContext.request.contextPath}/index" method="POST">
							<table class="controls">
								<tr>
									<td>Добавить новую запись</td>
									<td><input type="submit" name="NEW" value=">>"></td>
								</tr>
							</table>
						</form>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr id="footer">

		<td>
			<h3>Powered by Tass @ 2013</h3>
		</td>

	</tr>
</table>
</body>
</html>