package org.example.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entities.impl.CollectionTextDocuments;

import java.io.IOException;
import java.io.PrintWriter;

public class StructureServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CollectionTextDocuments colTextDoc = CollectionTextDocuments.getInstance();
        String structureCollection = colTextDoc.collectionStructure();
        req.setAttribute("structureCollection", structureCollection);

          RequestDispatcher requestDispatcher = req.getRequestDispatcher("pages/structure.jsp"); // получаем объект диспетчера запросов
          requestDispatcher.forward(req,resp); //передаем управление нашей страничке
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rootDirectory = req.getParameter("rootDirectory");
        CollectionTextDocuments colTextDoc = CollectionTextDocuments.getInstance();
        colTextDoc.traversingDirectoryTree(rootDirectory);
    }


}
